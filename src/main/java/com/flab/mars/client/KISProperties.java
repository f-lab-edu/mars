package com.flab.mars.client;

import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@ConfigurationProperties(prefix = "kis")
@ToString

public class KISProperties {

    private final Api api;
    private final Auth auth;


    public KISProperties(Api api, Auth auth) {
        this.api = api;
        this.auth = auth;
    }

    @Getter
    @ToString
    public static class Api {
        private final String baseUrl;

        public Api(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }

    @Getter
    @ToString
    public static class Auth {
        private final String grantType;
        private final String appKey;
        private final String appSecret;
        private final String accessToken;

        public Auth(String grantType, String appKey, String appSecret, String accessToken) {
            this.grantType = grantType;
            this.appKey = appKey;
            this.appSecret = appSecret;
            this.accessToken = accessToken;
        }
    }
}