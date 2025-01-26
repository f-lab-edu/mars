package com.flab.mars;

import com.flab.mars.db.entity.StockInfoEntity;
import com.flab.mars.db.repository.StockInfoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StockInfoInitializer {

    private final StockInfoRepository stockInfoRepository;

    public StockInfoInitializer(StockInfoRepository stockInfoRepository) {
        this.stockInfoRepository = stockInfoRepository;
    }

    @PostConstruct
    public void init() {
        // 주식 정보 데이터 삽입
        if (stockInfoRepository.count() == 0) {
            stockInfoRepository.saveAll(Arrays.asList(
                    StockInfoEntity.builder().stockCode("035420").stockName("NAVER").build(),
                    StockInfoEntity.builder().stockCode("005930").stockName("삼성전자").build(),
                    StockInfoEntity.builder().stockCode("035720").stockName("카카오").build(),
                    StockInfoEntity.builder().stockCode("000660").stockName("SK hynix").build(),
                    StockInfoEntity.builder().stockCode("005380").stockName("현대차").build(),
                    StockInfoEntity.builder().stockCode("051910").stockName("LG화학").build(),
                    StockInfoEntity.builder().stockCode("005490").stockName("POSCO").build(),
                    StockInfoEntity.builder().stockCode("000270").stockName("기아").build(),
                    StockInfoEntity.builder().stockCode("035000").stockName("셀트리온").build(),
                    StockInfoEntity.builder().stockCode("068270").stockName("셀트리온헬스케어").build(),
                    StockInfoEntity.builder().stockCode("036570").stockName("한국전력").build(),
                    StockInfoEntity.builder().stockCode("017670").stockName("SK텔레콤").build(),
                    StockInfoEntity.builder().stockCode("012330").stockName("롯데케미칼").build(),
                    StockInfoEntity.builder().stockCode("032640").stockName("KB금융").build(),
                    StockInfoEntity.builder().stockCode("005720").stockName("한화생명").build(),
                    StockInfoEntity.builder().stockCode("006800").stockName("삼성생명").build(),
                    StockInfoEntity.builder().stockCode("011200").stockName("삼성화재").build(),
                    StockInfoEntity.builder().stockCode("086790").stockName("우리금융지주").build(),
                    StockInfoEntity.builder().stockCode("033780").stockName("KT&G").build(),
                    StockInfoEntity.builder().stockCode("033520").stockName("LG전자").build(),
                    StockInfoEntity.builder().stockCode("051900").stockName("LG디스플레이").build(),
                    StockInfoEntity.builder().stockCode("035250").stockName("CJ ENM").build(),
                    StockInfoEntity.builder().stockCode("000810").stockName("SK네트웍스").build(),
                    StockInfoEntity.builder().stockCode("086280").stockName("한온시스템").build(),
                    StockInfoEntity.builder().stockCode("012750").stockName("현대모비스").build(),
                    StockInfoEntity.builder().stockCode("035720").stockName("카카오").build(),
                    StockInfoEntity.builder().stockCode("021240").stockName("대우건설").build(),
                    StockInfoEntity.builder().stockCode("086980").stockName("대림산업").build(),
                    StockInfoEntity.builder().stockCode("009150").stockName("삼성전기").build(),
                    StockInfoEntity.builder().stockCode("010950").stockName("SK이노베이션").build(),
                    StockInfoEntity.builder().stockCode("017800").stockName("한화에어로스페이스").build(),
                    StockInfoEntity.builder().stockCode("032830").stockName("롯데하이마트").build(),
                    StockInfoEntity.builder().stockCode("018260").stockName("현대건설").build(),
                    StockInfoEntity.builder().stockCode("018880").stockName("LG유플러스").build(),
                    StockInfoEntity.builder().stockCode("003550").stockName("두산중공업").build(),
                    StockInfoEntity.builder().stockCode("071050").stockName("한미약품").build(),
                    StockInfoEntity.builder().stockCode("047050").stockName("현대글로비스").build(),
                    StockInfoEntity.builder().stockCode("020000").stockName("쌍용차").build(),
                    StockInfoEntity.builder().stockCode("009830").stockName("하나금융지주").build(),
                    StockInfoEntity.builder().stockCode("096770").stockName("삼성SDI").build(),
                    StockInfoEntity.builder().stockCode("032640").stockName("KB금융").build(),
                    StockInfoEntity.builder().stockCode("004170").stockName("아모레퍼시픽").build(),
                    StockInfoEntity.builder().stockCode("003670").stockName("LG생활건강").build(),
                    StockInfoEntity.builder().stockCode("010130").stockName("이마트").build(),
                    StockInfoEntity.builder().stockCode("003000").stockName("CJ제일제당").build(),
                    StockInfoEntity.builder().stockCode("000810").stockName("SK네트웍스").build(),
                    StockInfoEntity.builder().stockCode("041190").stockName("삼성증권").build(),
                    StockInfoEntity.builder().stockCode("033180").stockName("한국항공우주").build(),
                    StockInfoEntity.builder().stockCode("010130").stockName("이마트").build(),
                    StockInfoEntity.builder().stockCode("005490").stockName("POSCO").build()
            ));
        }
    }
}
