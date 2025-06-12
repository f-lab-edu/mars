package com.flab.mars.domain.service;

import com.flab.mars.client.KISClient;
import com.flab.mars.client.KISProperties;
import com.flab.mars.client.dto.KisStockPriceDto;
import com.flab.mars.db.entity.StockInfoEntity;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockPriceSyncService {

    private final StockInfoFetcher stockInfoFetcher;
    private final StockPriceSaver stockPriceSaver;
    private final KISClient kisClient;
    private final KISProperties kisProperties;

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    private void syncStockPriceAsync(StockInfoEntity stockInfo) {
        executor.submit(() -> {
            try {
                KisStockPriceDto stockPrice = kisClient.getStockPrice(kisProperties.getAuth().getAccessToken(), kisProperties.getAuth().getAppKey(), kisProperties.getAuth().getAppSecret(), stockInfo.getStockCode());
                stockPriceSaver.storeStockPriceWithoutDuplication(stockPrice, stockInfo, LocalDateTime.now());
            } catch (Exception e) {
                log.error("StockPriceSyncService 동기화 중 에러 발생 for stock code: {}", stockInfo.getStockCode(), e);
            }
        });
    }

    public void syncStockPrices() {
        stockInfoFetcher.fetchPagedStocksStream().forEach(this::syncStockPriceAsync);
    }

    // 서비스 종료시 ExecutorService 를 종료하는 메서드
    @PreDestroy
    public void shutdownExecutorService() {
        if(!executor.isShutdown()) {
            executor.shutdown();
        }
    }
}
