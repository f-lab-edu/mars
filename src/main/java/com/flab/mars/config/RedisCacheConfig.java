package com.flab.mars.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flab.mars.domain.vo.response.PriceDataVO;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.Duration;

@Configuration
@EnableCaching //  Spring Boot 의 캐싱 설정을 활성화
public class RedisCacheConfig {

    @Bean
    public CacheManager stockPriceCacheManager(RedisConnectionFactory redisConnectionFactory) {

        // ObjectMapper 생성
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(new JavaTimeModule()) // java 8 날짜/시간 처리 모듈 등록
                .build();


        // Jackson2JsonRedisSerializer 생성시 objecMapper 설정
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                // Redis 에 Key 를 저장할 때 String 으로 직렬화(변환) 해서 저장
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new StringRedisSerializer()))
                // Redis 에 Value 를 저장할때 Json 으로 직렬화(변환) 해서 저장
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(objectMapper, PriceDataVO.class))
                )
                // 데이터의 만료기간(TTL 설정)
                .entryTtl(Duration.ofMinutes(1L));

        // 레디스 캐시 매니저 생성
        return RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();

    }
}
