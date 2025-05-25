package com.flab.mars.domain.vo;

import com.flab.mars.client.vo.KisAuthInfoVO;
import lombok.Value;

/**
 * 인증 정보 (불변 VO)
 */
@Value
public class AuthInfoVO {
    String appSecret;
    String appKey;
    String accessToken;

    public KisAuthInfoVO toKisAuthInfoVO() {
        return new KisAuthInfoVO(appSecret, appKey, accessToken);
    }
}
