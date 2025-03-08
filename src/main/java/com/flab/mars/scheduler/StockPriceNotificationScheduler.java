package com.flab.mars.scheduler;

import com.flab.mars.domain.service.StockPriceNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Component
public class StockPriceNotificationScheduler {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final StockPriceNotificationService stockPriceNotificationService;

    // +-5%를 기준으로 알림 보내기
    @Scheduled(fixedRate = 600000) // 10분주기
    public void sendPriceChangeNotification() {
        log.info("StockPriceNotificationScheduler started at {}", LocalDateTime.now().format(formatter));
        stockPriceNotificationService.notifyStockPrice();
        log.info("StockPriceNotificationScheduler completed at {}", LocalDateTime.now().format(formatter));
    }
}
