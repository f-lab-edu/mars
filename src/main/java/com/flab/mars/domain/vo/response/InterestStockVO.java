package com.flab.mars.domain.vo.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class InterestStockVO {
    private String stockCode;       // 주식 코드
    private String stockName;       // 주식 이름
    private String currentPrice;    // 현재가
    private String prdyVrss;        // 전일 대비
    private String prdyVrssSign;    // 전일 대비 부호
    private String prdyCtrt; // 전일 대비율
}
