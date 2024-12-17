package com.flab.mars.config;

import com.flab.mars.client.KISApiUrls;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RootConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl(KISApiUrls.BASE_URL) // KIS API의 기본 URL 설정
                .defaultHeader("Content-Type", "application/json") // 기본 헤더 설정
                .build();
    }
}
