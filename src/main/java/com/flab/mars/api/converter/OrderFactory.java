package com.flab.mars.api.converter;

import com.flab.mars.api.dto.request.BuyStockRequest;
import com.flab.mars.domain.vo.*;

public class OrderFactory {

    public static Order from(BuyStockRequest request) {
        OrderType orderType = OrderType.valueOf(request.getOrderType().toUpperCase());
        PriceType priceType = PriceType.valueOf(request.getPriceType().toUpperCase());

        if(PriceType.MARKET.equals(priceType)) {
            // 시장가
            return new MarketOrder(request.getStockCode(), request.getQuantity(), orderType, request.getIdempotencyKey());
        }

        /// 지정가 가격파라미터 필수
        if (request.getLimitPrice() == null) {
            throw new IllegalArgumentException("Limit price is required for LIMIT order.");
        }

        // 지정가
        return new LimitOrder(request.getStockCode(), request.getQuantity(), request.getLimitPrice(), orderType, request.getIdempotencyKey());

    }
}
