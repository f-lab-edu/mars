package com.flab.mars.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.mars.client.dto.KISFluctuationResponseDto;
import com.flab.mars.client.dto.KisStockPriceDto;
import com.flab.mars.exception.BadWebClientRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
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

    private final ObjectMapper mapper;

    public String getAccessToken(String appKey, String appSecret, String grantType) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", grantType);
        requestBody.put("appkey", appKey);
        requestBody.put("appsecret", appSecret);

        Map<String, String> result = webClient.post()
                .uri(GET_TOKEN)
                .headers(headers -> headers.setContentType(MediaType.APPLICATION_JSON))
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleClientError) // 4xx, 5xx 에러 처리
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {})
                .block();

        assert result != null;
        return result.get("access_token");

    }

    private Mono<Throwable> handleClientError(ClientResponse clientResponse) {
        HttpStatusCode status = clientResponse.statusCode();
        return clientResponse.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new BadWebClientRequestException(
                        body, status, mapper)));
    }

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
