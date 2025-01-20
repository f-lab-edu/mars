package com.flab.mars.api.dto.response;

import com.flab.mars.domain.vo.response.PriceDataVO;
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


    public static StockPriceDto from(PriceDataVO priceDataVO) {
        return new StockPriceDto(
                priceDataVO.getId(),
                priceDataVO.getStockInfoId(),
                priceDataVO.getCurrentPrice(),
                priceDataVO.getOpenPrice(),
                priceDataVO.getClosePrice(),
                priceDataVO.getHighPrice(),
                priceDataVO.getLowPrice(),
                priceDataVO.getDateTime());

    }
}