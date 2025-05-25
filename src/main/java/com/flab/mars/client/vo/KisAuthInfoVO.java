package com.flab.mars.client.vo;

import lombok.Value;

@Value
public class KisAuthInfoVO {
    String appSecret;
    String appKey;
    String accessToken;
}
