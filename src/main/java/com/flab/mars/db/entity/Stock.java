package com.flab.mars.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
public class Stock {

    /*
    주식 종목 코드
     */
    @Id
    private String stockSymbol;

    private String stockName;

    private BigDecimal currentPrice;

    private BigDecimal marketCap;

    private String sector;




}
