package com.flab.mars.domain.vo.order;

import com.flab.mars.client.dto.KisOrderStockVO;
import com.flab.mars.domain.vo.OrderType;
import com.flab.mars.domain.vo.PriceType;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 주식 지정가 주문시 가격 필드 필요
 */
@RequiredArgsConstructor
public class LimitPriceStrategy implements PriceStrategy {

    private final BigDecimal price;

    @Override
    public PriceType getPriceType() {
        return PriceType.LIMIT;
    }

    @Override
    public Optional<BigDecimal> getPrice() {
        return  Optional.of(price);
    }

    @Override
    public KisOrderStockVO toKisOrderStockVO(OrderContext orderContext, String accountPrefix, String accountSuffix) {
        return KisOrderStockVO.builder()
                .stockCode(orderContext.getStockCode())
                .orderType(orderContext.getOrderType().name())
                .priceType(PriceType.LIMIT.name())
                .quantity(orderContext.getQuantity())
                .limitPrice(this.price)
                .isBuy(OrderType.BUY.equals(orderContext.getOrderType()))
                .accountPrefix(accountPrefix)
                .accountSuffix(accountSuffix)
                .build();
    }
}
