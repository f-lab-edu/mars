package com.flab.mars.client;

public class KISApiUrls {

    /**
     * 모의 Domain
     */
    public static final String BASE_URL = "https://openapivts.koreainvestment.com:29443";

    /**
     * 접근토큰발급
     */
    public static final String GET_TOKEN = "/oauth2/tokenP";

    /**
     * 주식현재가 시세[v1_국내주식-008]
     */
    public static final String INQUIRE_PRICE = "/uapi/domestic-stock/v1/quotations/inquire-price";


    /**
     * 거래량순위[v1_국내주식-047]
     */
    public static final String RANKING_FLUCTUATION = "/uapi/domestic-stock/v1/ranking/fluctuation";

    private KISApiUrls() {
        throw new AssertionError("Cannot instantiate utility class");
    }

}