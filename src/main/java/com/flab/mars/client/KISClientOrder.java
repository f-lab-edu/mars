package com.flab.mars.client;

import com.flab.mars.client.dto.KisOrderResponseDto;
import com.flab.mars.client.dto.KisOrderStockVO;
import com.flab.mars.client.vo.KisAuthInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

import static com.flab.mars.client.constant.KisOrderResponseCode.OK;

@Slf4j
@Component
@RequiredArgsConstructor
public class KISClientOrder {

    private final WebClient webClient;

    public KisOrderResponseDto orderStock(KisOrderStockVO kisOrderStock, KisAuthInfoVO kisAuthInfo) {
        KisOrderResponseDto kisOrderResponseDto = webClient.post()
                .uri(KISApiUrls.ORDER_CASH) // 실제 주문 URI로 교체
                .headers(headers -> {
                    headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + kisAuthInfo.getAccessToken());
                    headers.set("appkey", kisAuthInfo.getAppKey());
                    headers.set("appsecret", kisAuthInfo.getAppSecret());
                    headers.set("tr_id", kisOrderStock.isBuy() ? "VTTC0012U" : "VTTC0011U"); // 예시: 매수/매도 구분
                    headers.set("custtype", "P"); // 고객 타입  P : 개인, B : 법인
                })
                .bodyValue(Map.of(
                        "CANO", kisOrderStock.getAccountPrefix(), // 계좌 앞 8자리
                        "ACNT_PRDT_CD", kisOrderStock.getAccountSuffix(), // 계좌 뒤 2자리
                        "PDNO", kisOrderStock.getStockCode(),
                        "ORD_DVSN", kisOrderStock.getPriceType().equals("MARKET") ? "01" : "00", // 예: "00" 지정가, "01" 시장가 등
                        "ORD_QTY", String.valueOf(kisOrderStock.getQuantity()),
                        "ORD_UNPR", kisOrderStock.getPriceType().equals("MARKET") ? "0" : String.valueOf(kisOrderStock.getLimitPrice()) // 주문단가 , 시장가 주문시, "0"으로 입력
                ))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("Order error response: {}", errorBody);
                                    return Mono.error(new RuntimeException("주문 요청 실패: " + errorBody));
                                }))
                .bodyToMono(KisOrderResponseDto.class)
                .block();

        if(!OK.equals(kisOrderResponseDto.getResultCode())) {
            log.error("KIS 주문 실패 resultCode={}, message={}", kisOrderResponseDto.getResultCode(), kisOrderResponseDto.getMessage());
            throw new RuntimeException("KIS 주문 실패: " + kisOrderResponseDto.getMessage());
        }

        return kisOrderResponseDto;

    }

}