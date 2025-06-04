package com.flab.mars.domain.service;

import com.flab.mars.client.KISClientOrder;
import com.flab.mars.client.dto.KisOrderStockVO;
import com.flab.mars.db.entity.*;
import com.flab.mars.db.repository.AccountRepository;
import com.flab.mars.db.repository.StockOrderRepository;
import com.flab.mars.domain.StockCodeValidator;
import com.flab.mars.domain.component.IdempotencyValidator;
import com.flab.mars.domain.component.MemberValidator;
import com.flab.mars.domain.vo.AuthInfoVO;
import com.flab.mars.domain.vo.order.OrderVO;
import com.flab.mars.domain.vo.response.OrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockOrderService {
    private final IdempotencyValidator idempotencyValidator;
    private final KISClientOrder kisClientOrder;
    private final MemberValidator memberValidator;

    private final StockOrderRepository stockOrderRepository;
    private final StockCodeValidator stockCodeValidator;
    private final AccountRepository accountRepository;

    @Transactional
    public OrderResult processOrder(OrderVO order, AuthInfoVO authInfo, Long userId) {

        // 멱등성 검사
        Optional<OrderResult> duplicateResult  = idempotencyValidator.validate(order.getIdempotencyKey());
        if (duplicateResult.isPresent()) {
            return duplicateResult.get();
        }

        // Member, StockInfo 조회
        MemberEntity member = memberValidator.validateExist(userId);

        StockInfoEntity stockInfoEntity = stockCodeValidator.validateExist(order.getStockCode());

        // 현재 사용중인 계좌 호출
        AccountEntity account  = accountRepository.findByMemberIdAndDefaultFlagTrue(member.getId()).orElseThrow(()-> new RuntimeException("기본 계좌가 없습니다."));

        StockOrderEntity orderEntity = createOrderEntity(order, member, stockInfoEntity);

        // 멱등키 중복시 유니크 제약조건 위배 exception을 발생시켜 데이터의 정합성을 보장함.
        stockOrderRepository.save(orderEntity);

        //  KIS API 주문 호출
        KisOrderStockVO kisOrderStockVO = order.toKisOrderStockVO(account.getAccountPrefix(), account.getAccountSuffix());

        // time out 시 1회 트라이
        try {
            kisClientOrder.orderStock(kisOrderStockVO, authInfo.toKisAuthInfoVO());
        } catch (WebClientRequestException e) {
            if (isTimeoutException(e)) {
                // TODO : KIS 주식일별주문체결 조회 API를 호출하아ㅕ 기존 주문 여부를 확인한 뒤,
                // 존재하지 않을 경우메만 재요청하도록 로직 개선 필요
                kisClientOrder.orderStock(kisOrderStockVO, authInfo.toKisAuthInfoVO());
            } else {
                orderEntity.markCanceled();
                throw e;
            }
        }

        orderEntity.markPending();

        return OrderResult.builder()
                .orderId(orderEntity.getId())
                .status(orderEntity.getOrderStatus().name().toLowerCase())
                .message("주문이 정상 처리되었습니다.")
                .build();

    }

    private static StockOrderEntity createOrderEntity(OrderVO order, MemberEntity member, StockInfoEntity stockInfoEntity) {
        return StockOrderEntity.builder()
                .member(member)
                .stockInfo(stockInfoEntity)
                .quantity(order.getQuantity())
                .pricePerUnit(order.getPriceStrategy().getPrice().orElse(null))
                .orderStatus(OrderStatus.REQUESTED)
                .orderType(OrderType.valueOf(order.getOrderType().name()))
                .orderDatetime(LocalDateTime.now())
                .idempotencyKey(order.getIdempotencyKey())
                .build();
    }

    private boolean isTimeoutException(WebClientRequestException e) {
        Throwable cause = e.getCause();
        return cause instanceof java.net.SocketTimeoutException ||
                cause instanceof java.net.ConnectException;
    }
}
