package com.flab.mars.api.dto.response;

import com.flab.mars.domain.vo.response.PriceDataResponseVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class StockPriceDto {

    private Long stockPriceId;

    private String stockInfoId; // StockInfoEntity의 ID와 연결되는 값

    private BigDecimal currentPrice; // 현재가
    private BigDecimal openPrice;    // 시가
    private BigDecimal closePrice;   // 종가
    private BigDecimal highPrice;    // 최고가
    private BigDecimal lowPrice;     // 최저가

    private BigDecimal acmlVol;      // 누적 거래량 (전체 누적 거래량)
    private BigDecimal acmlTrPbmn;   // 누적 거래 대금

    private LocalDate stockBusinessDate; // 주식 영업일자


    public static StockPriceDto from(PriceDataResponseVO priceDataResponseVO) {
        return new StockPriceDto(
                priceDataResponseVO.getId(),
                priceDataResponseVO.getStockInfoId(),
                priceDataResponseVO.getCurrentPrice(),
                priceDataResponseVO.getOpenPrice(),
                priceDataResponseVO.getClosePrice(),
                priceDataResponseVO.getHighPrice(),
                priceDataResponseVO.getLowPrice(),
                priceDataResponseVO.getAcmlVol(),
                priceDataResponseVO.getAcmlTrPbmn(),
                priceDataResponseVO.getStockBusinessDate());

    }
}
