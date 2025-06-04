package com.flab.mars.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyStockRequest {


    @NotBlank
    private String orderType;     // "buy" or "sell"

    @NotBlank
    private String priceType;     // "market" or "limit"

    @NotBlank
    private String stockCode;

    @NotNull
    private Integer quantity;

    private BigDecimal limitPrice; // 지정가 주문 시에만 사용

    @NotBlank
    private String idempotencyKey;

    //TODO 회원 가입 및 회원 관련 로직 추후 고려
    @NotNull
    private Long memberId;

    @NotNull(message = "필수 항목입니다.")
    private String appKey;

    @NotNull(message = "필수 항목입니다.")
    private String appSecret;

    @NotNull(message = "필수 항목입니다.")
    private String accessToken;
}
