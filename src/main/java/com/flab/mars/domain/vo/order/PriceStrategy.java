package com.flab.mars.domain.vo.order;

import com.flab.mars.client.dto.KisOrderStockVO;
import com.flab.mars.domain.vo.PriceType;

import java.math.BigDecimal;
import java.util.Optional;

public interface PriceStrategy {
    PriceType getPriceType();
    Optional<BigDecimal> getPrice();
    KisOrderStockVO toKisOrderStockVO(OrderContext orderContext, String accountPrefix, String accountSuffix);
}
