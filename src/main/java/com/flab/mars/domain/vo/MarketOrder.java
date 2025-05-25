package com.flab.mars.domain.vo;

import com.flab.mars.client.dto.KisOrderStockVO;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@EqualsAndHashCode
public class MarketOrder implements Order {
    private final String stockCode;
    private final int quantity;
    private final OrderType orderType;
    private final String idempotencyKey;


    @Override
    public String getStockCode() {
        return stockCode;
    }

    @Override
    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public PriceType getPriceType() {
        return PriceType.MARKET;
    }

    @Override
    public Optional<BigDecimal> getPrice() {
        return Optional.empty(); // 시장가 주문은 가격이 없음
    }

    @Override
    public KisOrderStockVO toKisOrderStockVO(String accountPrefix, String accountSuffix) {
        return KisOrderStockVO.builder()
                .stockCode(this.stockCode)
                .orderType(orderType.name())
                .priceType(PriceType.MARKET.name())
                .quantity(this.quantity)
                .isBuy(OrderType.BUY.equals(this.orderType))
                .accountPrefix(accountPrefix)
                .accountSuffix(accountSuffix)
                .build();
    }
}
