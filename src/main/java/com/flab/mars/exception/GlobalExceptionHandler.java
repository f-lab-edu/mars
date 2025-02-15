package com.flab.mars.exception;

import com.flab.mars.api.dto.response.ResultAPIDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // AuthException 예외 처리 메서드
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ResultAPIDto<Object>> authException(AuthException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ResultAPIDto.res(HttpStatus.UNAUTHORIZED, e.getMessage(), null));
    }
}
