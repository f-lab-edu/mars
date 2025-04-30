package com.flab.mars.domain.vo;

import com.flab.mars.client.dto.KisOrderStockVO;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderStockVO {

    private String stockCode;

    // 매수 여부 (true면 매수, false면 매도)
    private boolean isBuy;

    private String priceType;     // "market" or "limit"

    private String orderType;

    private Integer quantity;

    private BigDecimal limitPrice; // 지정가 주문 시에만 사용

    private String idempotencyKey;

    //TODO 회원 가입 및 회원 관련 로직 추후 고려
    private Long memberId;

    private String appKey;

    private String appSecret;

    private String accessToken;

    public KisOrderStockVO toKisOrderStockVO(String accountPrefix, String accountSuffix) {
        return KisOrderStockVO.builder()
                .stockCode(this.stockCode)
                .orderType(this.orderType)
                .priceType(this.priceType)
                .quantity(this.quantity)
                .limitPrice(this.limitPrice)
                .appKey(this.appKey)
                .appSecret(this.appSecret)
                .accessToken(this.accessToken)
                .isBuy("buy".equalsIgnoreCase(this.orderType))
                .accountPrefix(accountPrefix)
                .accountSuffix(accountSuffix)
                .build();
    }
}
