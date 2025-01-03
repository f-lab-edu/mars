package com.flab.mars.api.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InterestStockDto {

    private String stockName;
    private String stockCode;
    private String curPrice;
    private String priceChange; // 전일 대비 가격 변화
    private String priceChangeRate; // 전일 대비 변화률(%)

}