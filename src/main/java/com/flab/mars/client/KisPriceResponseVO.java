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

        @JsonProperty("stck_mxpr")
        private String stckMxpr; // 주식 상한가

        @JsonProperty("stck_llam")
        private String stckLlam; // 주식 하한가

        @JsonProperty("stck_sdpr")
        private String stckSdpr; // 주식 기준가

        @JsonProperty("wghn_avrg_stck_prc")
        private String wghnAvrgStckPrc; // 가중 평균 주식 가격

        @JsonProperty("hts_frgn_ehrt")
        private String htsFrgnEhrt; // HTS 외국인 소진율

        @JsonProperty("frgn_ntby_qty")
        private String frgnNtbyQty; // 외국인 순매수 수량

        @JsonProperty("pgtr_ntby_qty")
        private String pgtrNtbyQty; // 프로그램매매 순매수 수량

        @JsonProperty("stck_dryy_hgpr")
        private String stckDryyHgpr; // 주식 연중 최고가

        @JsonProperty("stck_dryy_lwpr")
        private String stckDryyLwpr; // 주식 연중 최저가

        @JsonProperty("w52_hgpr")
        private String w52Hgpr; // 52주일 최고가

        @JsonProperty("w52_lwpr")
        private String w52Lwpr; // 52주일 최저가

        @JsonProperty("whol_loan_rmnd_rate")
        private String wholLoanRmndRate; // 전체 융자 잔고 비율

        @JsonProperty("ssts_yn")
        private String sstsYn; // 공매도가능여부

        @JsonProperty("stck_shrn_iscd")
        private String stckShrnIscd; // 주식 단축 종목코드

        @JsonProperty("invt_caful_yn")
        private String invtCafulYn; // 투자유의여부

        @JsonProperty("mrkt_warn_cls_code")
        private String mrktWarnClsCode; // 시장경고코드

        @JsonProperty("short_over_yn")
        private String shortOverYn; // 단기과열여부

        @JsonProperty("sltr_yn")
        private String sltrYn; // 정리매매여부
    }

}