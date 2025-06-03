package com.flab.mars.client.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
public class KisOrderStockVO {

    private String stockCode;

    private String orderType;     // "buy" or "sell"

    private String priceType;     // "market" or "limit"

    private Integer quantity;

    private BigDecimal limitPrice; // 지정가 주문 시에만 사용


    // 매수 여부 (true면 매수, false면 매도)
    private final boolean isBuy;

    private String accountPrefix;  // CANO - 앞 8자리
    private String accountSuffix;  // ACNT_PRDT_CD - 뒤 2자리

}
