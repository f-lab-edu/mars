package com.flab.mars.domain.vo.response;

import com.flab.mars.db.entity.PriceDataEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PriceDataVO {

    private Long id;

    private Long stockInfoId; // StockInfoEntity의 ID와 연결되는 값

    private BigDecimal currentPrice; // 현재가
    private BigDecimal openPrice;    // 시가
    private BigDecimal closePrice;   // 종가
    private BigDecimal highPrice;    // 최고가
    private BigDecimal lowPrice;     // 최저가

    private BigDecimal accumulatedVolume;      // 누적 거래량 (전체 누적 거래량)
    private BigDecimal accumulatedTradeAmount;   // 누적 거래 대금

    private LocalDateTime dateTime;


    public static PriceDataVO toVO(PriceDataEntity entity) {
        return PriceDataVO.builder()
                .id(entity.getId())
                .stockInfoId(entity.getStockInfoEntity().getId())
                .currentPrice(new BigDecimal(entity.getCurrentPrice()))
                .openPrice(new BigDecimal(entity.getOpenPrice()))
                .closePrice(entity.getClosePrice() != null ? new BigDecimal(entity.getClosePrice()) : BigDecimal.ZERO)  // 실시간 가격일 경우 종가 없음
                .highPrice(new BigDecimal(entity.getHighPrice()))
                .lowPrice(new BigDecimal(entity.getLowPrice()))
                .accumulatedVolume(new BigDecimal(entity.getAccumulatedVolume()))
                .accumulatedTradeAmount(new BigDecimal(entity.getAccumulatedTradeAmount()))
                .dateTime(entity.getDateTime())
                .build();
    }
}