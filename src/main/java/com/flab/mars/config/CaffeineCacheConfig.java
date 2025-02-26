package com.flab.mars.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@EnableCaching // Spring Boot 의 캐싱 설정 활성화
public class CaffeineCacheConfig {


//     Caffeine 캐시 설정하기 - Caffeine 캐시 내부 알고리즘은 LFU와 LRU의 장점을 통합함
    @Bean
    public CacheManager stockPriceCacheManager() {
        // CacheManager Spring 프레임워크에서 캐시를 관리하는 인터페이스

        CaffeineCacheManager cacheManager = new CaffeineCacheManager("getStockPrice");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .initialCapacity(100) // 초기 용량
                .maximumSize(1000) // 최대 저장 갯수
                .expireAfterAccess(1, TimeUnit.MINUTES) //  데이터의 만료기간(TTL 설정)
        );
        return cacheManager;
    }

}
