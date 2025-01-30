package com.flab.mars.config;

import com.flab.mars.client.KISApiUrls;
import io.netty.channel.ChannelOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class RootConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl(KISApiUrls.BASE_URL) // KIS API의 기본 URL 설정
                .defaultHeader("content-type", "application/json; charset=utf-8") // 기본 헤더 설정
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)// 연결 타임아웃 5초
                                .option(ChannelOption.SO_TIMEOUT, 10) // 소켓 타임 아웃
                ))
                .build();
    }
}
