package com.flab.mars.api.controller;

import com.flab.mars.api.dto.request.ApiCredentialsRequest;
import com.flab.mars.api.dto.response.ResultAPIDto;
import com.flab.mars.domain.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stock")
@Slf4j
public class StockController {

    private final StockService stockService;

    /**
     * KIS 토큰 발급 요청, 1분당 하나 가능
     * @param request
     * @return
     */
    @PostMapping({"/accessToken"})
    public ResponseEntity<ResultAPIDto<Map<String, Object>>> getAccessToken(@RequestBody ApiCredentialsRequest request) {
        try {
            ResponseEntity<Map> response = stockService.getAccessToken(request.getAppKey(), request.getAppSecret());
            // 성공
            if (response.getStatusCode() == HttpStatus.OK) {
                // 성공적으로 access_token을 가져왔을 경우
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("access_token", response.getBody().get("access_token"));
                return ResponseEntity.ok(ResultAPIDto.res(HttpStatus.OK, "Success", responseBody));
            }
        }catch (HttpClientErrorException e){
            // 로그 남기기
            log.info(e.getMessage());

            if(e.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                // 접근토큰 발급 잠시 후 다시 시도하세요(1분당 1회)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResultAPIDto.res(HttpStatus.FORBIDDEN, e.getMessage()));
            }
        }

        // 기타 실패 응답 처리
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResultAPIDto.res(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"));
    }

}