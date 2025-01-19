package com.flab.mars.db.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "stock_info")
public class StockInfoEntity {
    @Id
    @GeneratedValue
    @Column(name = "stock_info_id")
    private Long id;

    private String stockCode; // 주식 코드
    private String stockName; // 주식 이름

}
