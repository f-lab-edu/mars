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
    private StockPriceDetails  stockPriceDetails;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StockPriceDetails {
        @JsonProperty("stck_prpr")
        private String currentPrice; // 주식 현재가

        @JsonProperty("prdy_vrss")
        private String priceChange; // 전일 대비

        @JsonProperty("prdy_vrss_sign")
        private String priceChangeSign; // 전일 대비 부호

        @JsonProperty("prdy_ctrt")
        private String priceChangeRate; // 전일 대비율

        @JsonProperty("acml_tr_pbmn")
        private String accumulatedTradeAmount; // 누적 거래 대금

        @JsonProperty("acml_vol")
        private String accumulatedVolume; // 누적 거래량

        @JsonProperty("stck_oprc")
        private String openingPrice; // 주식 시가

        @JsonProperty("stck_hgpr")
        private String highPrice; // 주식 최고가

        @JsonProperty("stck_lwpr")
        private String lowPrice; // 주식 최저가
    }

}
