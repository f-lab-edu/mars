package com.flab.mars.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(
        name = "price_data",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_stock_info_datetime", columnNames = {"stock_info_id", "date_time"})
        }
)
@ToString
public class PriceDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_info_id", nullable = false)
    private StockInfoEntity stockInfoEntity;

    private String currentPrice; // 현재가
    private String openPrice;    // 시가
    private String closePrice;   // 종가
    private String highPrice;    // 최고가
    private String lowPrice;     // 최저가

    private String accumulatedVolume;      // 누적 거래량 (전체 누적 거래량)
    private String accumulatedTradeAmount;   // 누적 거래 대금

    private String priceChange; // 전일 대비
    private String priceChangeSign; // 전일 대비 부호 1 : 상한,  2 : 상승, 3 : 보합 ,4 : 하한,  5 : 하락
    private String priceChangeRate; // 전일 대비율

    private LocalDateTime dateTime; // 데이터 발생 시간 (날짜+시간)

    @PrePersist
    public void prePersist() {
        if (dateTime == null) {
            this.dateTime = LocalDateTime.now();
        }
    }

}