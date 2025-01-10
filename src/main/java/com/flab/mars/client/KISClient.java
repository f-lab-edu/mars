package com.flab.mars.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.mars.exception.BadWebClientRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static com.flab.mars.client.KISApiUrls.GET_TOKEN;

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

}
