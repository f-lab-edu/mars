package com.flab.mars.client;

import com.flab.mars.domain.vo.StockPrice;
import com.flab.mars.domain.vo.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static com.flab.mars.client.KISApiUrls.GET_TOKEN;
import static com.flab.mars.client.KISApiUrls.INQUIRE_PRICE;

@Component
@RequiredArgsConstructor
public class KISClient {

    private final WebClient webClient;

    public TokenInfo getAccessToken(String appKey, String appSecret, String grantType) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", grantType);
        requestBody.put("appkey", appKey);
        requestBody.put("appsecret", appSecret);

        return webClient.post()
                .uri(GET_TOKEN)
                .headers(headers -> headers.setContentType(MediaType.APPLICATION_JSON))
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(
                        status -> status == HttpStatus.FORBIDDEN, // 403 상태 코드만 체크
                        response -> response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException(
                                        "403 오류: 접근이 거부되었습니다. 오류 메시지: " + errorBody
                                ))))
                .bodyToMono(TokenInfo.class)
                .block();

    }

    public StockPrice getStockPrice(String accessToken, String appKey, String appSecret, String stockCode) {
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
                .bodyToMono(StockPrice.class)
                .block();
    }
}
