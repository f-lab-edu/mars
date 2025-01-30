package com.flab.mars.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 한투 API 요청시 필수값
 */
@AllArgsConstructor
@Getter
public class TokenInfoVO {
    private String appKey;
    private String appSecret;
    private String accessToken;
}
