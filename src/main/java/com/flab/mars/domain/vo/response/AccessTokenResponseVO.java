package com.flab.mars.domain.vo.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;


@Getter
@AllArgsConstructor
public class AccessTokenResponseVO {
    private boolean success;
    private String message;
    private HttpStatusCode statusCode;
    private String accessToken;
}
