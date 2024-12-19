package com.flab.mars.domain.vo.request;

import com.flab.mars.client.KISApiUrls;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class StockFluctuationRequestVO {
    @Builder.Default
    private String fidRsflRate2 = ""; // Default: ""

    @Builder.Default
    private String fidCondMrktDivCode = "J"; // Default: "J"

    @Builder.Default
    private String fidCondScrDivCode = "20170"; // Default: "20170"

    @Builder.Default
    private String fidInputIscd = "0001"; // Default: "0001"

    @Builder.Default
    private String fidRankSortClsCode = "0"; // Default: "0"

    @Builder.Default
    private String fidInputCnt1 = "0"; // Default: "0"

    @Builder.Default
    private String fidPrcClsCode = "0"; // Default: "0"

    @Builder.Default
    private String fidInputPrice1 = ""; // Default: ""

    @Builder.Default
    private String fidInputPrice2 = ""; // Default: ""

    @Builder.Default
    private String fidVolCnt = ""; // Default: ""

    @Builder.Default
    private String fidTrgtClsCode = "0"; // Default: "0"

    @Builder.Default
    private String fidTrgtExlsClsCode = "0"; // Default: "0"

    @Builder.Default
    private String fidDivClsCode = "0"; // Default: "0"

    @Builder.Default
    private String fidRsflRate1 = ""; // Default: ""

    public String generateFluctuationRankingUri() {
        return UriComponentsBuilder
                .fromUriString(KISApiUrls.RANKING_FLUCTUATION)
                .queryParam("fid_input_iscd", fidInputIscd)
                .queryParam("fid_rank_sort_cls_code", fidRankSortClsCode)
                .queryParam("fid_input_cnt_1", fidInputCnt1)
                .queryParam("fid_prc_cls_code", fidPrcClsCode)
                .queryParam("fid_input_price_1", fidInputPrice1)
                .queryParam("fid_input_price_2", fidInputPrice2)
                .queryParam("fid_vol_cnt", fidVolCnt)
                .queryParam("fid_trgt_cls_code", fidTrgtClsCode)
                .queryParam("fid_trgt_exls_cls_code", fidTrgtExlsClsCode)
                .queryParam("fid_div_cls_code", fidDivClsCode)
                .queryParam("fid_cond_mrkt_div_code", fidCondMrktDivCode)
                .queryParam("fid_cond_scr_div_code", fidCondScrDivCode)
                .queryParam("fid_rsfl_rate2", fidRsflRate2)
                .queryParam("fid_rsfl_rate1", fidRsflRate1)
                .toUriString();
    }
}
