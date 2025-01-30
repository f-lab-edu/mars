package com.flab.mars.domain.service;

import com.flab.mars.client.KISClient;
import com.flab.mars.client.KISConfig;
import com.flab.mars.client.dto.KisStockPriceDto;
import com.flab.mars.db.entity.StockInfoEntity;
import com.flab.mars.db.repository.StockInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockPriceSyncService {

    private final StockInfoRepository stockInfoRepository;
    private final StockPriceService stockPriceService;
    private final KISClient kisClient;
    private final KISConfig kisConfig;


    public void syncStockPrices() {
        List<StockInfoEntity> allStockInfoEntity = stockInfoRepository.findAll();
        for (StockInfoEntity stockInfo : allStockInfoEntity) {
            try {
                KisStockPriceDto stockPrice = kisClient.getStockPrice(kisConfig.getAccessToken(), kisConfig.getAppKey(), kisConfig.getAppSecret(), stockInfo.getStockCode());
                stockPriceService.saveCurrentStockPrice(stockPrice, stockInfo, LocalDateTime.now());
            } catch (Exception e) {
                log.error("StockPriceSyncService 동기화 중 에러 발생 for stock code: {}", stockInfo.getStockCode(), e);
            }
        }
    }
}
