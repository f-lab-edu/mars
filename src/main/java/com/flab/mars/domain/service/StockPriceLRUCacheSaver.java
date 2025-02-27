package com.flab.mars.domain.service;

import com.flab.mars.domain.vo.LRUCache;
import com.flab.mars.domain.vo.response.PriceDataVO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StockPriceLRUCacheSaver {

    private static final String KEY_FORMAT = "stock::%s::time::%s";
    private final LRUCache<PriceDataVO> lruCache = LRUCache.createWithDefaultCapacity();

    private String generateKey(String stockCode, LocalDateTime localDate) {
        return KEY_FORMAT.formatted(stockCode, localDate.toString());
    }

    public void save(String stockCode, LocalDateTime localDate, PriceDataVO value) {
        String key = generateKey(stockCode, localDate);
        lruCache.add(key, value);
    }

    public PriceDataVO getIfPresent(String stockCode, LocalDateTime localDate) {
        String key = generateKey(stockCode, localDate);
        return lruCache.getIfPresent(key);
    }

}