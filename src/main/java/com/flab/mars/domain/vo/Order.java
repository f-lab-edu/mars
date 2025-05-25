package com.flab.mars.domain.vo;

import com.flab.mars.client.dto.KisOrderStockVO;

import java.math.BigDecimal;
import java.util.Optional;

public interface Order {
    String getStockCode();
    String getIdempotencyKey();
    int getQuantity();
    OrderType getOrderType();
    PriceType getPriceType();
    Optional<BigDecimal> getPrice();

    KisOrderStockVO toKisOrderStockVO(String accountPrefix, String accountSuffix);
}

