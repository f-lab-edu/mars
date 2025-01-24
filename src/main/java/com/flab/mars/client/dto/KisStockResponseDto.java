package com.flab.mars.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KisStockResponseDto {
    @JsonProperty("rt_cd")
    private String rtCd;               // 성공 실패 여부

    @JsonProperty("msg_cd")
    private String msgCd;              // 응답코드
    private String msg1;               // 응답메세지
    private Output output;             // 응답상세1
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Output {
        @JsonProperty("prdt_name")
        private String prdtName;          // 상품명

        @JsonProperty("prdt_eng_name")
        private String prdtEngName;       // 상품영문명

        @JsonProperty("prdt_abrv_name")
        private String prdtAbrvName;     // 상품약어명
    }
}
