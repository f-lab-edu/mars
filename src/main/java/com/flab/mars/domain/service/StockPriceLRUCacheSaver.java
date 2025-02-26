package com.flab.mars.domain.service;

import com.flab.mars.domain.vo.LRUCache;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StockPriceLRUCacheSaver {

    private final LRUCache<Object> lruCache = LRUCache.createWithDefaultCapacity();
    private static final String KEY_FORMAT = "stock::%s::time::%s";

    private String generateKey(String stockCode, LocalDateTime localDate) {
        return KEY_FORMAT.formatted(stockCode, localDate.toString());
    }

    public <T> void save(String stockCode, LocalDateTime localDate, T value) {
        String key = generateKey(stockCode, localDate);
        lruCache.add(key, value);
    }

    public <T> T getIfPresent(String stockCode, LocalDateTime localDate) {
        String key = generateKey(stockCode, localDate);
        return (T)lruCache.getIfPresent(key);
    }

}