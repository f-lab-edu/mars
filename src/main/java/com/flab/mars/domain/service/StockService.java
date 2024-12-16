package com.flab.mars.domain.service;

import com.flab.mars.client.KISConfig;
import com.flab.mars.domain.vo.StockPrice;
import com.flab.mars.domain.vo.TokenInfo;
import com.flab.mars.exception.AuthException;
import com.flab.mars.support.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

import static com.flab.mars.client.KISApiUrls.GET_TOKEN;
import static com.flab.mars.client.KISApiUrls.INQUIRE_PRICE;


@Service
@RequiredArgsConstructor
public class StockService {


    @Autowired
    private final RestTemplate  restTemplate;

    @Autowired
    private final KISConfig kisConfig;


    public TokenInfo getAccessToken(String appKey, String appSecret, HttpSession session) {
        Map<String, String> requestBody  = new HashMap<>();
        requestBody.put("grant_type", kisConfig.getGrantType());
        requestBody.put("appkey", appKey);
        requestBody.put("appsecret", appSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json; charset=utf-8");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        // appkey , appsecret 기반으로 토큰가져오기
        ResponseEntity<TokenInfo> response = restTemplate.postForEntity(GET_TOKEN, entity, TokenInfo.class);

        // 성공적인 응답 (200 OK)
        if (response.getStatusCode() == HttpStatus.OK) {
            TokenInfo  tokenInfo = response.getBody();
            if (tokenInfo != null &&  tokenInfo.getAccessToken() != null) {
                tokenInfo.init(appKey, appSecret);
                SessionUtil.setSessionAccessToKenValue(session, tokenInfo);
                return tokenInfo;
            } else {
                throw new RuntimeException("Access token not found in response.");
            }
        }

        // 기타 응답 상태 코드 처리
        else {
            throw new RuntimeException("Failed to obtain access token. Response code: " + response.getStatusCode());
        }
    }

    public StockPrice getStockPrice(String stockCode, HttpSession session) {
        TokenInfo tokenInfo = SessionUtil.getSessionAccessToKenValue(session);

        if(tokenInfo == null) {
            throw new AuthException("로그인에 실패했습니다. ACCESS 토큰을 가져올 수 없습니다.");
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(INQUIRE_PRICE)
                .queryParam("FID_COND_MRKT_DIV_CODE", "J")
                .queryParam("FID_INPUT_ISCD", stockCode);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokenInfo.getAccessToken());
        headers.set("appkey", tokenInfo.getAppKey());
        headers.set("appsecret", tokenInfo.getAppSecret());
        headers.set("tr_id", "FHKST01010100"); // 주식현재가 시세
        headers.set("Content-Type", "application/json; charset=utf-8");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<StockPrice> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, StockPrice.class);

        return response.getBody();

    }
}
