package com.flab.mars.db.repository;

import com.flab.mars.db.entity.PriceDataEntity;
import com.flab.mars.db.entity.StockInfoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PriceDataRepositoryTest {

    @Autowired
    private PriceDataRepository priceDataRepository;

    @Autowired
    private StockInfoRepository stockInfoRepository;
    @Test
    void testUniqueConstraintViolation(){
        //given
        StockInfoEntity stockInfoEntity = StockInfoEntity.builder()
                .stockCode("12345")
                .stockName("삼성전자")
                .build();

        stockInfoRepository.save(stockInfoEntity);

        LocalDateTime localDateTime = LocalDateTime.of(2025, 2,14,10,0);

        PriceDataEntity priceData1 = PriceDataEntity.builder()
                .stockInfoEntity(stockInfoEntity)
                .dateTime(localDateTime)
                .currentPrice("50000")
                .build();

        priceDataRepository.save(priceData1);

        // when & then
        PriceDataEntity priceData2 = PriceDataEntity.builder()
                .stockInfoEntity(stockInfoEntity)
                .dateTime(localDateTime)
                .currentPrice("60000")
                .build();


        assertThrows(DataIntegrityViolationException.class, () -> priceDataRepository.saveAndFlush(priceData2));
    }
}