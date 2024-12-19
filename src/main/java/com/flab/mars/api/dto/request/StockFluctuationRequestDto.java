package com.flab.mars.api.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
