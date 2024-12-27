package com.flab.mars.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KISFluctuationResponseDto {
    @JsonProperty("rt_cd")
    private String rtCd;

    private String msg1;

    @JsonProperty("output")
    private List<KISFluctuationResponseDto.StockFluctuationDto> output;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StockFluctuationDto {
        @JsonProperty("stck_shrn_iscd")
        private String stockCode;

        @JsonProperty("data_rank")
        private String dataRank;

        @JsonProperty("hts_kor_isnm")
        private String stockName;

        @JsonProperty("stck_prpr")
        private String stockPrice;

        @JsonProperty("prdy_vrss")
        private String priceChange;

        @JsonProperty("prdy_vrss_sign")
        private String priceChangeSign;

        @JsonProperty("prdy_ctrt")
        private String priceChangeRate;
    }
}
