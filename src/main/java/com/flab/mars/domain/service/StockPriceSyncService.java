package com.flab.mars.domain.service;

import com.flab.mars.client.KISClient;
import com.flab.mars.client.KISConfig;
import com.flab.mars.client.dto.KisStockPriceDto;
import com.flab.mars.db.entity.StockInfoEntity;
import com.flab.mars.db.repository.StockInfoRepository;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockPriceSyncService {

    private final StockInfoRepository stockInfoRepository;
    private final StockPriceService stockPriceService;
    private final KISClient kisClient;
    private final KISConfig kisConfig;


    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    // TODO - api 초당 호출 횟수 20회 제한 처리 필요
    private static final int PAGE_SIZE = 20;

    private void syncStockPriceAsync(StockInfoEntity stockInfo) {
        executor.submit(() -> {
            try {
                KisStockPriceDto stockPrice = kisClient.getStockPrice(kisConfig.getAccessToken(), kisConfig.getAppKey(), kisConfig.getAppSecret(), stockInfo.getStockCode());
                stockPriceService.saveCurrentStockPrice(stockPrice, stockInfo, LocalDateTime.now());
            } catch (Exception e) {
                log.error("StockPriceSyncService 동기화 중 에러 발생 for stock code: {}", stockInfo.getStockCode(), e);
            }
        });
    }


    public void syncStockPrices() {
        int page = 0;
        boolean hasMore = true;

        while (hasMore) {
            Pageable pageable = PageRequest.of(page, PAGE_SIZE);
            Page<StockInfoEntity> stockInfoPage = stockInfoRepository.findAll(pageable);
            List<StockInfoEntity> stockInfoEntities  = stockInfoPage.getContent();
            stockInfoEntities.forEach(this::syncStockPriceAsync);

            hasMore = stockInfoPage.hasNext();
            page++;
        }
   }

   // 서비스 종료시 ExecutorService 를 종료하는 메서드
   @PreDestroy
   public void shutdownExecutorService() {
        if(!executor.isShutdown()) {
            executor.shutdown();
        }
   }
}
