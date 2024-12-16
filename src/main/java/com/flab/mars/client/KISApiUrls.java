package com.flab.mars.client;

public class KISApiUrls {

    /**
     * 모의 Domain
     */
    public static final String BASE_URL = "https://openapivts.koreainvestment.com:29443";

    /**
     * 접근토큰발급
     */
    public static final String GET_TOKEN = BASE_URL + "/oauth2/tokenP";

    private KISApiUrls() {
        throw new AssertionError("Cannot instantiate utility class");
    }

}