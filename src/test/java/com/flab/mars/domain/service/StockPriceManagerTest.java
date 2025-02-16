package com.flab.mars.domain.service;

import com.flab.mars.client.dto.KisStockPriceDto;
import com.flab.mars.db.entity.PriceDataEntity;
import com.flab.mars.db.entity.StockInfoEntity;
import com.flab.mars.db.repository.PriceDataRepository;
import com.flab.mars.domain.vo.response.PriceDataVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class StockPriceManagerTest {

    @InjectMocks
    private StockPriceSaver stockPriceSaver;
    @Mock
    private PriceDataRepository priceDataRepository;
    private StockInfoEntity stockInfoEntity;
    private LocalDateTime currentTime;
    private KisStockPriceDto kisStockPriceDto;
    @BeforeEach
    void setUp() {

        kisStockPriceDto = new KisStockPriceDto();
        KisStockPriceDto.StockPriceDetails stockPriceDetails = new KisStockPriceDto.StockPriceDetails();
        stockPriceDetails.setCurrentPrice("1000");
        stockPriceDetails.setOpeningPrice("950");
        stockPriceDetails.setHighPrice("1050");
        stockPriceDetails.setLowPrice("940");
        stockPriceDetails.setAccumulatedVolume("2000");
        stockPriceDetails.setAccumulatedTradeAmount("2000000");
        stockPriceDetails.setPriceChange("50");
        stockPriceDetails.setPriceChangeSign("+");
        stockPriceDetails.setPriceChangeRate("5.0");
        kisStockPriceDto.setStockPriceDetails(stockPriceDetails);

        stockInfoEntity = StockInfoEntity.builder()
                .stockCode("12345")
                .stockName("APPLE").build();

        currentTime = LocalDateTime.now();
    }

    @Test
    void testSaveCurrentStockPrice_Success() {
        // given
        KisStockPriceDto.StockPriceDetails stockDetails = kisStockPriceDto.getStockPriceDetails();
        PriceDataEntity priceDataEntity = PriceDataEntity.builder()
                .stockInfoEntity(stockInfoEntity)
                .currentPrice(stockDetails.getCurrentPrice())       // 현재가
                .openPrice(stockDetails.getOpeningPrice())          // 시가
                .highPrice(stockDetails.getHighPrice())             // 최고가
                .lowPrice(stockDetails.getLowPrice())               // 최저가
                .accumulatedVolume(stockDetails.getAccumulatedVolume()) // 누적 거래량
                .accumulatedTradeAmount(stockDetails.getAccumulatedTradeAmount()) // 누적 거래 대금
                .priceChange(stockDetails.getPriceChange())         // 전일 대비 가격 변화
                .priceChangeRate(stockDetails.getPriceChangeRate()) // 전일 대비율
                .dateTime(currentTime)
                .build();

        when(priceDataRepository.save(any(PriceDataEntity.class))).thenReturn(priceDataEntity);

        // when
        PriceDataVO priceDataVO = stockPriceSaver.storeStockPriceWithoutDuplication(kisStockPriceDto, stockInfoEntity, currentTime);

        //then
        assertNotNull(priceDataVO);
        assertEquals(new BigDecimal("1000"), priceDataVO.getCurrentPrice());
        verify(priceDataRepository, times(1)).save(any(PriceDataEntity.class));
    }


}