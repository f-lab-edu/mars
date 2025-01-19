package com.flab.mars.api.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

@Data
@NoArgsConstructor
public class StockFluctuationRequestDto {

    @Size(max = 12, message = "입력 종목코드는 12자이어야 합니다.")
    private String fidInputIscd = "0001";

    @Size(max = 2, message = "순위 정렬 구분 코드는 2자이어야 합니다.")
    private String fidRankSortClsCode = "0";

    @Size(max = 12, message = "입력 수1은 12자이어야 합니다.")
    private String fidInputCnt1 = "0";

    @Size(max = 2, message = "가격 구분 코드는 2자이어야 합니다.")
    private String fidPrcClsCode = "0";

    @Size(max = 12, message = "입력 가격1은 12자이어야 합니다.")
    private String fidInputPrice1 = "";

    @Size(max = 12, message = "입력 가격2은 12자이어야 합니다.")
    private String fidInputPrice2 = "";

    @Size(max = 12, message = "거래량 수는 12자이어야 합니다.")
    private String fidVolCnt = "";

    private String fidTrgtClsCode = "0"; // Default: "0"

    private String fidTrgtExlsClsCode = "0"; // Default: "0"

    private String fidDivClsCode = "0"; // Default: "0"

    private String fidRsflRate1 = ""; // Default: ""

    private String fidCondMrktDivCode = "J"; // Default: "J"

    private String fidCondScrDivCode = "20170"; // Default: "20170"

    private String fidRsflRate2 = ""; // Default: ""


    public String buildQueryParams() {
        return UriComponentsBuilder.newInstance()
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
