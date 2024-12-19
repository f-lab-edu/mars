package com.flab.mars.domain.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.flab.mars.domain.vo.response.BaseResponseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockPrice extends BaseResponseVO {
    @JsonProperty("output")
    private Output output;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Output {
        @JsonProperty("stck_prpr")
        private String stckPrpr; // 주식 현재가
    }

    public StockPrice(String rtCd, String msg1, Output output) {
        super(rtCd, msg1);
        this.output = output;
    }

}