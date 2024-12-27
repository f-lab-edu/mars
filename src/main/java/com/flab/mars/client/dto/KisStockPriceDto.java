package com.flab.mars.client.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KisStockPriceDto {
    @JsonProperty("rt_cd")
    private String rtCd;

    private String msg1;

    @JsonProperty("output")
    private Output output;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Output {
        @JsonProperty("stck_prpr")
        private String stckPrpr; // 주식 현재가
    }

}
