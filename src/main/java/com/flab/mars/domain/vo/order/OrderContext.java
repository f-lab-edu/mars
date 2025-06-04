package com.flab.mars.domain.vo.order;


import com.flab.mars.domain.vo.OrderType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderContext {
    private final String stockCode;
    private final Integer quantity;
    private final OrderType orderType;
}
