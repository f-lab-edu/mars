package com.flab.mars.client;

import com.flab.mars.client.dto.KISFluctuationResponseDto;
import com.flab.mars.client.dto.KisStockPriceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import static com.flab.mars.client.KISApiUrls.INQUIRE_PRICE;

@Component
@RequiredArgsConstructor
public class KISClient {

    private final WebClient webClient;


    public KisStockPriceDto getStockPrice(String accessToken, String appKey, String appSecret, String stockCode) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(INQUIRE_PRICE)
                        .queryParam("FID_COND_MRKT_DIV_CODE", "J")
                        .queryParam("FID_INPUT_ISCD", stockCode)
                        .build())
                .headers(headers -> {
                    headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
                    headers.set("appkey", appKey);
                    headers.set("appsecret", appSecret);
                    headers.set("tr_id", "FHKST01010100");
                    headers.setContentType(MediaType.APPLICATION_JSON);
                })
                .retrieve()
                .bodyToMono(KisStockPriceDto.class)
                .block();
    }

    public KISFluctuationResponseDto getFluctuationRanking(String accessToken, String appKey, String appSecret, String uri) {
        return  webClient.method(HttpMethod.GET)
                .uri(uri)
                .headers(headers -> {
                    headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
                    headers.set("appkey", appKey);
                    headers.set("appsecret", appSecret);
                    headers.set("tr_id", "FHPST01700000");
                    headers.set("custtype", "P"); // 고객 타입  P : 개인, B : 법인
                })
                .retrieve()
                .bodyToMono(KISFluctuationResponseDto.class)
                .block(); // 블록해서 결과를 가져옴
    }
}
