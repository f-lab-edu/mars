package com.flab.mars.client;


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
public class KisPriceResponseVO extends BaseResponseVO {
    @JsonProperty("output")
    private Output output;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Output {
        @JsonProperty("stck_prpr")
        private String stckPrpr; // 주식 현재가

        @JsonProperty("prdy_vrss")
        private String prdyVrss; // 전일 대비

        @JsonProperty("prdy_vrss_sign")
        private String prdyVrssSign; // 전일 대비 부호

        @JsonProperty("prdy_ctrt")
        private String prdyCtrt; // 전일 대비율

        @JsonProperty("acml_tr_pbmn")
        private String acmlTrPbmn; // 누적 거래 대금

        @JsonProperty("acml_vol")
        private String acmlVol; // 누적 거래량

        @JsonProperty("stck_oprc")
        private String stckOprc; // 주식 시가

        @JsonProperty("stck_hgpr")
        private String stckHgpr; // 주식 최고가

        @JsonProperty("stck_lwpr")
        private String stckLwpr; // 주식 최저가

    }

}