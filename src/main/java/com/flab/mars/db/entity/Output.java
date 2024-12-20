package com.flab.mars.db.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Embeddable
public class Output {

    @Column(name = "stck_prpr")
    private String stckPrpr; // 주식 현재가

    @Column(name = "prdy_vrss")
    private String prdyVrss; // 전일 대비

    @Column(name = "prdy_vrss_sign")
    private String prdyVrssSign; // 전일 대비 부호

    @Column(name = "prdy_ctrt")
    private String prdyCtrt; // 전일 대비율

    @Column(name = "acml_tr_pbmn")
    private String acmlTrPbmn; // 누적 거래 대금

    @Column(name = "acml_vol")
    private String acmlVol; // 누적 거래량

    @Column(name = "stck_oprc")
    private String stckOprc; // 주식 시가

    @Column(name = "stck_hgpr")
    private String stckHgpr; // 주식 최고가

    @Column(name = "stck_lwpr")
    private String stckLwpr; // 주식 최저가

    @Column(name = "stck_mxpr")
    private String stckMxpr; // 주식 상한가

    @Column(name = "stck_llam")
    private String stckLlam; // 주식 하한가

    @Column(name = "stck_sdpr")
    private String stckSdpr; // 주식 기준가

    @Column(name = "wghn_avrg_stck_prc")
    private String wghnAvrgStckPrc; // 가중 평균 주식 가격

    @Column(name = "hts_frgn_ehrt")
    private String htsFrgnEhrt; // HTS 외국인 소진율

    @Column(name = "frgn_ntby_qty")
    private String frgnNtbyQty; // 외국인 순매수 수량

    @Column(name = "pgtr_ntby_qty")
    private String pgtrNtbyQty; // 프로그램매매 순매수 수량

    @Column(name = "stck_dryy_hgpr")
    private String stckDryyHgpr; // 주식 연중 최고가

    @Column(name = "stck_dryy_lwpr")
    private String stckDryyLwpr; // 주식 연중 최저가

    @Column(name = "w52_hgpr")
    private String w52Hgpr; // 52주일 최고가

    @Column(name = "w52_lwpr")
    private String w52Lwpr; // 52주일 최저가

    @Column(name = "whol_loan_rmnd_rate")
    private String wholLoanRmndRate; // 전체 융자 잔고 비율

    @Column(name = "ssts_yn")
    private String sstsYn; // 공매도가능여부

    @Column(name = "stck_shrn_iscd")
    private String stckShrnIscd; // 주식 단축 종목코드

    @Column(name = "invt_caful_yn")
    private String invtCafulYn; // 투자유의여부

    @Column(name = "mrkt_warn_cls_code")
    private String mrktWarnClsCode; // 시장경고코드

    @Column(name = "short_over_yn")
    private String shortOverYn; // 단기과열여부

    @Column(name = "sltr_yn")
    private String sltrYn; // 정리매매여부
}
