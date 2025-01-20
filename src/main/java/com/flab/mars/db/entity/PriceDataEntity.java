package com.flab.mars.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "price_data")
@ToString
public class PriceDataEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_info_id")
    private StockInfoEntity stockInfoEntity;

    private String currentPrice; // 현재가 (데이터 유형이 실기간인 경우 사용)
    private String openPrice;    // 시가
    private String closePrice;   // 종가
    private String highPrice;    // 최고가
    private String lowPrice;     // 최저가

    private String acmlVol;      // 누적 거래량 (전체 누적 거래량)
    private String acmlTrPbmn;   // 누적 거래 대금

    private String prdyVrss; // 전일 대비
    private String prdyVrssSign; // 전일 대비 부호 1 : 상한,  2 : 상승, 3 : 보합 ,4 : 하한,  5 : 하락
    private String prdyCtrt; // 전일 대비율

    private LocalDateTime dateTime; // 데이터 발생 시간 (날짜+시간)

    @PrePersist
    public void prePersist() {
        if (dateTime == null) {
            this.dateTime = LocalDateTime.now();
        }
    }

}