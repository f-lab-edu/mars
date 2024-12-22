package com.flab.mars.domain.vo.response;

import com.flab.mars.db.entity.PriceDataEntity;
import com.flab.mars.db.entity.PriceDataType;
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
public class PriceDataResponseVO {

    private Long id;

    private String stockInfoId; // StockInfoEntity의 ID와 연결되는 값

    private PriceDataType dataType; // 데이터 유형 (DAY, WEEK, MONTH, YEAR)

    private BigDecimal currentPrice; // 현재가
    private BigDecimal openPrice;    // 시가
    private BigDecimal closePrice;   // 종가
    private BigDecimal highPrice;    // 최고가
    private BigDecimal lowPrice;     // 최저가

    private BigDecimal acmlVol;      // 누적 거래량 (전체 누적 거래량)
    private BigDecimal acmlTrPbmn;   // 누적 거래 대금

    private LocalDate stockBusinessDate; // 주식 영업일자


    public static PriceDataResponseVO toVO(PriceDataEntity entity) {
        return PriceDataResponseVO.builder()
                .id(entity.getId())
                //.stockInfoId(entity.getStockInfoEntity().getId().toString()) // TODO : StockPriceInfoEntity의 ID를 문자열로 변환
                .dataType(entity.getDataType())
                .currentPrice(new BigDecimal(entity.getCurrentPrice()))
                .openPrice(new BigDecimal(entity.getOpenPrice()))
                .closePrice(entity.getClosePrice() != null ? new BigDecimal(entity.getClosePrice()) : BigDecimal.ZERO)  // 실시간 가격일 경우 종가 없음
                .highPrice(new BigDecimal(entity.getHighPrice()))
                .lowPrice(new BigDecimal(entity.getLowPrice()))
                .acmlVol(new BigDecimal(entity.getAcmlVol()))
                .acmlTrPbmn(new BigDecimal(entity.getAcmlTrPbmn()))
                .stockBusinessDate(entity.getStockBusinessDate())
                .build();
    }
}
