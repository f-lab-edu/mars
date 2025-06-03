package com.flab.mars.domain.vo.order;

import com.flab.mars.client.dto.KisOrderStockVO;
import com.flab.mars.domain.vo.OrderType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 주식 주문시 사용되는 클래스,
 * 단일 책임 원칙을 만족하도록 PriceStrategy 인터페이스로 시장가와 지정가를 분기처리
 */
@RequiredArgsConstructor
@Getter
public class OrderVO {
    private final String stockCode;
    private final int quantity;
    private final OrderType orderType;
    private final String idempotencyKey;
    private final PriceStrategy priceStrategy;

    public KisOrderStockVO toKisOrderStockVO(String accountPrefix, String accountSuffix){
        OrderContext context = new OrderContext(stockCode, quantity, orderType);
        return priceStrategy.toKisOrderStockVO(context, accountPrefix, accountSuffix);
    }
}
