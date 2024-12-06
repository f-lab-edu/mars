package com.flab.mars.db.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
 현금 배당
 */

@Entity
@Getter
public class CashDividend {

    @Id
    @GeneratedValue
    @Column(name = "dividend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    /*
    주당 배당금
     */
    private BigDecimal dividendAmount;

    private LocalDateTime distributionDate;

    /*
     주식회사명
     */
    private String stockName;


}
