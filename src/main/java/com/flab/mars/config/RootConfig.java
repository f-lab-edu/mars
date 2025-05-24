package com.flab.mars.config;

import com.flab.mars.client.KISApiUrls;
import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class RootConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl(KISApiUrls.BASE_URL) // KIS API의 기본 URL 설정
                .defaultHeader("content-type", "application/json; charset=utf-8") // 기본 헤더 설정
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // 연결 타임아웃 5초
                                .doOnConnected(conn -> conn
                                        .addHandlerLast(new ReadTimeoutHandler(10, TimeUnit.SECONDS))
                                        .addHandlerLast(new WriteTimeoutHandler(10))
                                )
                                .responseTimeout(Duration.ofSeconds(10)) // 응답 타임아웃 10초 추가
                ))
                .build();
    }

    @Bean
    public CountedAspect countedAspect(MeterRegistry registry) {
        return new CountedAspect(registry); // CountedAspect 를 등록하면  @Counted 를 인지해서 Counter 를 사용하는 AOP 를 적용한다.
    }
}
