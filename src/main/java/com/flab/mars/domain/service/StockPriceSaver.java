package com.flab.mars.domain.service;

import com.flab.mars.client.dto.KisStockPriceDto;
import com.flab.mars.db.entity.PriceDataEntity;
import com.flab.mars.db.entity.StockInfoEntity;
import com.flab.mars.db.repository.PriceDataRepository;
import com.flab.mars.domain.vo.response.PriceDataVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockPriceSaver  {

    private final PriceDataRepository priceDataRepository;

    /**
     * 현재 주식 가격을 DB에 저장합니다.
     * @param stockPriceDto 주식 가격 정보
     * @param stockInfoEntity 주식 정보 테이블
     * @param currentTime 현재 시간
     * @return 주식 가격 VO 객체
     */
    public PriceDataVO storeStockPriceWithoutDuplication(KisStockPriceDto stockPriceDto, StockInfoEntity stockInfoEntity, LocalDateTime currentTime) {

        KisStockPriceDto.StockPriceDetails stockDetails = stockPriceDto.getStockPriceDetails();

        // PriceDataEntity 빌드
        PriceDataEntity priceDataEntity = PriceDataEntity.builder()
                .stockInfoEntity(stockInfoEntity)
                .currentPrice(stockDetails.getCurrentPrice())       // 현재가
                .openPrice(stockDetails.getOpeningPrice())          // 시가
                .highPrice(stockDetails.getHighPrice())             // 최고가
                .lowPrice(stockDetails.getLowPrice())               // 최저가
                .accumulatedVolume(stockDetails.getAccumulatedVolume()) // 누적 거래량
                .accumulatedTradeAmount(stockDetails.getAccumulatedTradeAmount()) // 누적 거래 대금
                .priceChange(stockDetails.getPriceChange())         // 전일 대비 가격 변화
                .priceChangeSign(mapPriceChangeSign(stockDetails.getPriceChangeSign())) // 전일 대비 부호
                .priceChangeRate(stockDetails.getPriceChangeRate()) // 전일 대비율
                .dateTime(currentTime)                              // 현재 시간
                .build();

        try {
            PriceDataEntity savedPriceDataEntity = priceDataRepository.save(priceDataEntity);
            return PriceDataVO.toVO(savedPriceDataEntity);
        } catch (DataIntegrityViolationException e) {
            // 이미 존재하는 데이터라면기존 데이터 조회 후 반환
            return priceDataRepository.findByStockInfoEntityAndDateTime(stockInfoEntity, currentTime)
                    .map(PriceDataVO::toVO)
                    .orElseThrow(()-> new IllegalStateException("데이터 조회 중 오류 발생"));
        }

    }


    private String mapPriceChangeSign(String prdyVrssSign) {
        return switch (prdyVrssSign) {
            case "1", "2" -> // 상승
                    "+";
            case "4", "5" ->  // 하락
                    "-";
            default -> ""; // 기호 없음
        };
    }
}
