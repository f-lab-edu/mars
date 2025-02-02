package com.flab.mars.db.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "stock_info")
public class StockInfoEntity {
    @Id
    @GeneratedValue
    @Column(name = "stock_info_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String stockCode; // 주식 코드

    @Column(nullable = false)
    private String stockName; // 주식 이름

    public StockInfoEntity(String stockCode, String stockName) {
        this.stockCode = stockCode;
        this.stockName = stockName;
    }
}
