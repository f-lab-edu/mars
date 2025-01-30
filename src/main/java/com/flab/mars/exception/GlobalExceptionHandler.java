package com.flab.mars.exception;

import com.flab.mars.api.dto.response.ResultAPIDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // AuthException 예외 처리 메서드
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ResultAPIDto<Object>> authException(AuthException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ResultAPIDto.res(HttpStatus.UNAUTHORIZED, e.getMessage(), null));
    }

    @ExceptionHandler(WebClientRequestException.class)
    private ResponseEntity<ResultAPIDto<String>> connectTimeoutException1(WebClientRequestException e) {
        String errorMessage = e.getMessage() != null ? e.getMessage() : "Unexpected error occurred.";
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ResultAPIDto.res(HttpStatus.SERVICE_UNAVAILABLE, errorMessage, "연결 중 에러가 발생하였습니다."));
    }
}
