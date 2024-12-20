package com.flab.mars.domain.vo.response;


import com.flab.mars.db.entity.StockPriceChartEntity;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockPriceResponseVO extends BaseResponseVO {
    private Output output;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Output {
        private String stckPrpr; // 주식 현재가

        private String prdyVrss; // 전일 대비

        private String prdyVrssSign; // 전일 대비 부호

        private String prdyCtrt; // 전일 대비율

        private String acmlTrPbmn; // 누적 거래 대금

        private String acmlVol; // 누적 거래량

        private String stckOprc; // 주식 시가

        private String stckHgpr; // 주식 최고가

        private String stckLwpr; // 주식 최저가

        private String stckMxpr; // 주식 상한가

        private String stckLlam; // 주식 하한가

        private String stckSdpr; // 주식 기준가

        private String wghnAvrgStckPrc; // 가중 평균 주식 가격

        private String htsFrgnEhrt; // HTS 외국인 소진율

        private String frgnNtbyQty; // 외국인 순매수 수량

        private String pgtrNtbyQty; // 프로그램매매 순매수 수량

        private String stckDryyHgpr; // 주식 연중 최고가

        private String stckDryyLwpr; // 주식 연중 최저가

        private String w52Hgpr; // 52주일 최고가

        private String w52Lwpr; // 52주일 최저가

        private String wholLoanRmndRate; // 전체 융자 잔고 비율

        private String sstsYn; // 공매도가능여부

        private String stckShrnIscd; // 주식 단축 종목코드

        private String invtCafulYn; // 투자유의여부

        private String mrktWarnClsCode; // 시장경고코드

        private String shortOverYn; // 단기과열여부

        private String sltrYn; // 정리매매여부
    }

    public static StockPriceResponseVO toVO(StockPriceChartEntity stockPriceChart){
        Output output = Output.builder()
                .stckPrpr(stockPriceChart.getOutput().getStckPrpr())
                .prdyVrss(stockPriceChart.getOutput().getPrdyVrss())
                .prdyVrssSign(stockPriceChart.getOutput().getPrdyVrssSign())
                .prdyCtrt(stockPriceChart.getOutput().getPrdyCtrt())
                .acmlTrPbmn(stockPriceChart.getOutput().getAcmlTrPbmn())
                .acmlVol(stockPriceChart.getOutput().getAcmlVol())
                .stckOprc(stockPriceChart.getOutput().getStckOprc())
                .stckHgpr(stockPriceChart.getOutput().getStckHgpr())
                .stckLwpr(stockPriceChart.getOutput().getStckLwpr())
                .stckMxpr(stockPriceChart.getOutput().getStckMxpr())
                .stckLlam(stockPriceChart.getOutput().getStckLlam())
                .stckSdpr(stockPriceChart.getOutput().getStckSdpr())
                .wghnAvrgStckPrc(stockPriceChart.getOutput().getWghnAvrgStckPrc())
                .htsFrgnEhrt(stockPriceChart.getOutput().getHtsFrgnEhrt())
                .frgnNtbyQty(stockPriceChart.getOutput().getFrgnNtbyQty())
                .pgtrNtbyQty(stockPriceChart.getOutput().getPgtrNtbyQty())
                .stckDryyHgpr(stockPriceChart.getOutput().getStckDryyHgpr())
                .stckDryyLwpr(stockPriceChart.getOutput().getStckDryyLwpr())
                .w52Hgpr(stockPriceChart.getOutput().getW52Hgpr())
                .w52Lwpr(stockPriceChart.getOutput().getW52Lwpr())
                .wholLoanRmndRate(stockPriceChart.getOutput().getWholLoanRmndRate())
                .sstsYn(stockPriceChart.getOutput().getSstsYn())
                .stckShrnIscd(stockPriceChart.getOutput().getStckShrnIscd())
                .invtCafulYn(stockPriceChart.getOutput().getInvtCafulYn())
                .mrktWarnClsCode(stockPriceChart.getOutput().getMrktWarnClsCode())
                .shortOverYn(stockPriceChart.getOutput().getShortOverYn())
                .sltrYn(stockPriceChart.getOutput().getSltrYn())
                .build();

        return StockPriceResponseVO.builder()
                .output(output)
                .build();
    }


}