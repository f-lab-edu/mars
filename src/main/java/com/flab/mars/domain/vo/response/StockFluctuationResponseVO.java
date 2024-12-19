package com.flab.mars.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StockFluctuationResponseVO extends BaseResponseVO {
    @JsonProperty("output")
    private List<StockFluctuationVO> output;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StockFluctuationVO {
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
