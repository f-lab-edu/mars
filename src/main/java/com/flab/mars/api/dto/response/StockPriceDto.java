package com.flab.mars.api.dto.response;

import com.flab.mars.domain.vo.response.PriceDataResponseVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class StockPriceDto {

    private Long stockPriceId;

    private Long stockInfoId; // StockInfoEntity의 ID와 연결되는 값

    private BigDecimal currentPrice; // 현재가
    private BigDecimal openPrice;    // 시가
    private BigDecimal closePrice;   // 종가
    private BigDecimal highPrice;    // 최고가
    private BigDecimal lowPrice;     // 최저가

    private LocalDateTime dateTime;


    public static StockPriceDto from(PriceDataResponseVO priceDataResponseVO) {
        return new StockPriceDto(
                priceDataResponseVO.getId(),
                priceDataResponseVO.getStockInfoId(),
                priceDataResponseVO.getCurrentPrice(),
                priceDataResponseVO.getOpenPrice(),
                priceDataResponseVO.getClosePrice(),
                priceDataResponseVO.getHighPrice(),
                priceDataResponseVO.getLowPrice(),
                priceDataResponseVO.getDateTime());

    }
}
