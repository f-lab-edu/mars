package com.flab.mars.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "price_data")
public class PriceDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_info_id")
    private StockInfoEntity stockInfoEntity;

    @Enumerated(EnumType.STRING)
    private PriceDataType dataType; // 데이터 유형 (DAY, WEEK, MONTH, YEAR)

    private String currentPrice; // 현재가 (데이터 유형이 실기간인 경우 사용)
    private String openPrice;    // 시가
    private String closePrice;   // 종가
    private String highPrice;    // 최고가
    private String lowPrice;     // 최저가

    private String acmlVol;      // 누적 거래량 (전체 누적 거래량)
    private String acmlTrPbmn;   // 누적 거래 대금

    @Column(name = "stock_business_date")
    private LocalDate stockBusinessDate; // 주식 영업일자

    @PrePersist
    public void prePersist() {
        if (stockBusinessDate == null) {
            this.stockBusinessDate = LocalDate.now();
        }
    }

}
