package com.flab.mars.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Setter;

@Data
public class TokenInfo {

    @Setter
    @JsonProperty("access_token")         // JSON 키와 필드 이름을 매핑
    private String accessToken;           // 접근토큰

    @Setter
    @JsonProperty("access_token_token_expired") // JSON 키와 필드 이름을 매핑
    private String accessTokenExpired;    // 토큰 만료 시간

    @Setter
    @JsonProperty("token_type")
    private String tokenType;           // 토큰 타입

    @Setter
    @JsonProperty("expires_in")
    private long expiresIn;             // 남은 유효 기간 (초 단위)

    private String appKey;
    private String appSecret;

    public TokenInfo(String appKey, String appSecret) {
        this.appKey = appKey;
        this.appSecret = appSecret;
    }

}
