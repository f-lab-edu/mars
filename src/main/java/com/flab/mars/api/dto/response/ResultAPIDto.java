package com.flab.mars.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@Getter
public class ResultAPIDto<T> {
    private HttpStatus statusCode;
    private String resultMsg;
    private T resultData;

    public ResultAPIDto(final HttpStatus statusCode, final String message) {
        this.statusCode = statusCode;
        this.resultMsg = message;
        this.resultData = null ;
    }

    public static<T> ResultAPIDto<T> res(final HttpStatus statusCode, final String resultMsg) {
        return res(statusCode, resultMsg, null);
    }

    public static<T> ResultAPIDto<T> res(final HttpStatus statusCode, final String resultMsg, final T t) {
        return ResultAPIDto.<T>builder()
                .resultData(t)
                .statusCode(statusCode)
                .resultMsg(resultMsg)
                .build();
    }
}
