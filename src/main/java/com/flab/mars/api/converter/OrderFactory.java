package com.flab.mars.api.converter;

import com.flab.mars.api.dto.request.BuyStockRequest;
import com.flab.mars.domain.vo.*;
import com.flab.mars.domain.vo.order.LimitPriceStrategy;
import com.flab.mars.domain.vo.order.MarketPriceStrategy;
import com.flab.mars.domain.vo.order.OrderVO;

public class OrderFactory {

    public static OrderVO from(BuyStockRequest request) {
        OrderType orderType = OrderType.valueOf(request.getOrderType().toUpperCase()); // BUY, SELL
        PriceType priceType = PriceType.valueOf(request.getPriceType().toUpperCase());

        if(PriceType.MARKET.equals(priceType)) {
            // 시장가
            return new OrderVO(request.getStockCode(), request.getQuantity(), orderType, request.getIdempotencyKey(), new MarketPriceStrategy());
        }

        /// 지정가 가격파라미터 필수
        if (request.getLimitPrice() == null) {
            throw new IllegalArgumentException("Limit price is required for LIMIT order.");
        }

        // 지정가
        return new OrderVO(request.getStockCode(), request.getQuantity(), orderType, request.getIdempotencyKey(), new LimitPriceStrategy(request.getLimitPrice()));

    }
}
