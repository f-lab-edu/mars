package com.flab.mars.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Data
public class StockPrice {

    @JsonProperty("rt_cd")
    private String rtCd;

    private String msg1;

    @JsonProperty("output")
    private Output output;

    @Data
    private class Output {
        @JsonProperty("stck_prpr")
        private String stckPrpr; // 주식 현재가
    }

}