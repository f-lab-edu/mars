package com.flab.mars.client;

public class KISApiUrls {

    /**
     * 실전 Domain
     */
    public static final String BASE_URL = "https://openapi.koreainvestment.com:9443";

    /**
     * 접근토큰발급
     */
    public static final String GET_TOKEN = "/oauth2/tokenP";

    /**
     * 주식현재가 시세[v1_국내주식-008]
     */
    public static final String INQUIRE_PRICE = "/uapi/domestic-stock/v1/quotations/inquire-price";


    /**
     * 국내주식 등락률 순위[v1_국내주식-088]
     */
    public static final String RANKING_FLUCTUATION = "/uapi/domestic-stock/v1/ranking/fluctuation";

    /**
     * 상품기본조회[v1_국내주식-029]
     */
    public static final String SEARCH_INFO = "/uapi/domestic-stock/v1/quotations/search-info";

    private KISApiUrls() {
        throw new AssertionError("Cannot instantiate utility class");
    }

}