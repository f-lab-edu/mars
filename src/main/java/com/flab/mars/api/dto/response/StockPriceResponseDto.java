package com.flab.mars.api.dto.response;

import com.flab.mars.db.entity.PriceDataType;
import com.flab.mars.domain.vo.response.PriceDataResponseVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class StockPriceResponseDto {

    private Long id;

    private String stockId; // StockInfoEntity의 ID와 연결되는 값

    private PriceDataType dataType;

    private BigDecimal currentPrice; // 현재가
    private BigDecimal openPrice;    // 시가
    private BigDecimal closePrice;   // 종가
    private BigDecimal highPrice;    // 최고가
    private BigDecimal lowPrice;     // 최저가

    private BigDecimal acmlVol;      // 누적 거래량 (전체 누적 거래량)
    private BigDecimal acmlTrPbmn;   // 누적 거래 대금

    private LocalDate stockBusinessDate; // 주식 영업일자

    // PriceDataResponseVO 객체를 StockPriceResponseDto로 변환하는 메서드
    public static StockPriceResponseDto from(PriceDataResponseVO responseVO) {
        return StockPriceResponseDto.builder()
                .id(responseVO.getId())
                .dataType(responseVO.getDataType())
                .currentPrice(responseVO.getCurrentPrice())
                .openPrice(responseVO.getOpenPrice())
                .closePrice(responseVO.getClosePrice())
                .highPrice(responseVO.getHighPrice())
                .lowPrice(responseVO.getLowPrice())
                .acmlVol(responseVO.getAcmlVol())
                .acmlTrPbmn(responseVO.getAcmlTrPbmn())
                .stockBusinessDate(responseVO.getStockBusinessDate())
                .build();
    }
}
