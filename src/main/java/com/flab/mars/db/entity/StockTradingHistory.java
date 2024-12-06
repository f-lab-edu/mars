package com.flab.mars.db.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Getter
public class StockTradingHistory  {

    @Id
    @GeneratedValue
    @Column(name = "trade_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id" , nullable = false)
    private Account account;

    /*
     주식 종목 코드
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stockSymbol", nullable = false)
    private Stock stock;

    /*
    거래 진행 상태 (예: INITIATED, FILLED, CANCELLED 등)
     */
    @Enumerated(EnumType.STRING)
    private TradeProgress status;

    /*
     주문 타입 (예: 매수, 매도)
     */
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    /*
    주당 거래 가격
     */
    private BigDecimal pricePerUnit;


    /*
        총 거래 금액
     */
    private BigDecimal totalPrice;

    /*
    거래 수량
     */
    private int quantity;

    /*
    주문 시간
     */
    private LocalDateTime orderTime;


}
