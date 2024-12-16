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

    /**
     * 주식현재가 시세[v1_국내주식-008]
     */
    public static final String INQUIRE_PRICE = BASE_URL + "/uapi/domestic-stock/v1/quotations/inquire-price";

    private KISApiUrls() {
        throw new AssertionError("Cannot instantiate utility class");
    }

}