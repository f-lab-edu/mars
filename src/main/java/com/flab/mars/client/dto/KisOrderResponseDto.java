package com.flab.mars.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KisOrderResponseDto {
    @JsonProperty("rt_cd")
    private String resultCode;  // 성공 여부 (정상: "0", 실패: "1")

    @JsonProperty("msg_cd")
    private String messageCode; // 응답 코드

    @JsonProperty("msg1")
    private String message;     // 응답 메시지

    @JsonProperty("output")
    private List<OrderResult> orderResults;  // 주문 결과 목록

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderResult {

        @JsonProperty("odno")
        private String orderNumber; // 주문번호

        @JsonProperty("ord_tmd")
        private String orderTime;   // 주문시간 (HHmmss)
    }

}
