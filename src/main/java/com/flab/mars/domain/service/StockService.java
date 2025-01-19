package com.flab.mars.domain.service;

import com.flab.mars.client.KISApiUrls;
import com.flab.mars.client.KISConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class StockService {


    @Autowired
    private final RestTemplate  restTemplate;

    @Autowired
    private final KISConfig kisConfig;


    public ResponseEntity<Map> getAccessToken(String appKey, String appSecret) {
        Map<String, String> requestBody  = new HashMap<>();
        requestBody.put("grant_type", kisConfig.getGrantType());
        requestBody.put("appkey", appKey);
        requestBody.put("appsecret", appSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("charset", "UTF-8");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        // appkey , appsecret 기반으로 토큰가져오기
        ResponseEntity<Map> response = restTemplate.postForEntity(KISApiUrls.GET_TOKEN, entity, Map.class);

        // 성공적인 응답 (200 OK)
        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("access_token")) {
                // access_token을 포함하여 반환
                Map<String, Object> result = new HashMap<>();
                result.put("status", HttpStatus.OK);
                result.put("access_token", responseBody.get("access_token"));
                return ResponseEntity.ok(result);
            } else {
                throw new RuntimeException("Access token not found in response.");
            }
        }

        // 기타 응답 상태 코드 처리
        else {
            throw new RuntimeException("Failed to obtain access token. Response code: " + response.getStatusCode());
        }
    }
}
