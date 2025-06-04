package com.flab.mars.domain.vo.order;

import com.flab.mars.client.dto.KisOrderStockVO;
import com.flab.mars.domain.vo.OrderType;
import com.flab.mars.domain.vo.PriceType;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 주식 시장가 주문 시 사용
 */
@AllArgsConstructor
public class MarketPriceStrategy implements PriceStrategy {
    @Override
    public PriceType getPriceType() {
        return PriceType.MARKET;
    }

    @Override
    public Optional<BigDecimal> getPrice() {
        return Optional.empty();  // 시장가는 가격 없음
    }

    @Override
    public KisOrderStockVO toKisOrderStockVO(OrderContext orderContext, String accountPrefix, String accountSuffix) {
        return KisOrderStockVO.builder()
                .stockCode(orderContext.getStockCode())
                .orderType(orderContext.getOrderType().name())
                .priceType(PriceType.MARKET.name())
                .quantity(orderContext.getQuantity())
                .isBuy(OrderType.BUY.equals(orderContext.getOrderType()))
                .accountPrefix(accountPrefix)
                .accountSuffix(accountSuffix)
                .build();
    }
}
