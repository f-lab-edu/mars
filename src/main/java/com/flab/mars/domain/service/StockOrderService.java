package com.flab.mars.domain.service;

import com.flab.mars.client.KISClientOrder;
import com.flab.mars.client.dto.KisOrderStockVO;
import com.flab.mars.db.entity.*;
import com.flab.mars.db.repository.AccountRepository;
import com.flab.mars.db.repository.StockOrderRepository;
import com.flab.mars.domain.StockCodeValidator;
import com.flab.mars.domain.component.IdempotencyValidator;
import com.flab.mars.domain.component.MemberValidator;
import com.flab.mars.domain.vo.OrderStockVO;
import com.flab.mars.domain.vo.response.OrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public OrderResult processOrder(OrderStockVO orderStockVO) {

        // 멱등성 검사
        Optional<OrderResult> duplicateResult  = idempotencyValidator.validate(orderStockVO.getIdempotencyKey());
        if (duplicateResult.isPresent()) {
            return duplicateResult.get();
        }

        // Member, StockInfo 조회
        MemberEntity member = memberValidator.validateExist(orderStockVO.getMemberId());

        StockInfoEntity stockInfoEntity = stockCodeValidator.validateExist(orderStockVO.getStockCode());

        // 현재 사용중인 계좌 호출
        AccountEntity account  = accountRepository.findByMemberIdAndIsDefaultTrue(member.getId()).orElseThrow(()-> new RuntimeException("기본 계좌가 없습니다."));
        //  KIS API 주문 호출
        KisOrderStockVO kisOrderStockVO = orderStockVO.toKisOrderStockVO(account.getAccountPrefix(), account.getAccountSuffix());

        // time out 시 1회 트라이
        try {
            kisClientOrder.orderStock(kisOrderStockVO);
        } catch (WebClientRequestException e) {
            if (isTimeoutException(e)) {
                kisClientOrder.orderStock(kisOrderStockVO);
            } else {
                throw e;
            }
        }

        StockOrderEntity orderEntity = StockOrderEntity.builder()
                .member(member)
                .stockInfo(stockInfoEntity)
                .quantity(orderStockVO.getQuantity())
                .pricePerUnit(orderStockVO.getLimitPrice())
                .orderStatus(OrderStatus.PENDING)
                .orderType(orderStockVO.isBuy() ? OrderType.BUY : OrderType.SELL)
                .orderDatetime(LocalDateTime.now())
                .idempotencyKey(orderStockVO.getIdempotencyKey())
                .build();

        stockOrderRepository.save(orderEntity);

        return OrderResult.builder()
                .orderId(orderEntity.getId())
                .status(orderEntity.getOrderStatus().name().toLowerCase())
                .message("주문이 정상 처리되었습니다.")
                .build();

    }

    private boolean isTimeoutException(WebClientRequestException e) {
        Throwable cause = e.getCause();
        return cause instanceof java.net.SocketTimeoutException ||
                cause instanceof java.net.ConnectException;
    }
}
