package com.flab.mars.scheduler;

import com.flab.mars.domain.service.StockPriceSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockPriceSyncScheduler {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final StockPriceSyncService stockPriceSyncService;


    @Scheduled(cron = "* */10 * * * *") // 10분마다 업데이트
    public void syncStockPrice() {
        log.info("Stock price sync started at {}", LocalDateTime.now().format(formatter));
        stockPriceSyncService.syncStockPrices();
        log.info("Stock price sync completed at {}", LocalDateTime.now().format(formatter));
    }

}
