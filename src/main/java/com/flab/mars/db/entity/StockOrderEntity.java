package com.flab.mars.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ToString
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock_order")
public class StockOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_info_id")
    private StockInfoEntity stockInfo;

    @Column(nullable = false)
    private int quantity;

    @Column
    private BigDecimal pricePerUnit; // 주당 거래 가격

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderType orderType;

    @Column(nullable = false)
    private LocalDateTime orderDatetime;

    @Column(name = "idempotency_key", nullable = false, unique = true, length = 100)
    private String idempotencyKey;

    // == 비즈니스로직 ==
    //  도메인의 책임을 객체에 집중시켜 코드의 일관성관 유지 보수정을 높힘.
    //  도메인의 중요한 상태전이나 규칙이 있을때, set 메서드보다 의미있는 동작을 표현하고 싶을때

    public void markPending() {
        this.orderStatus = OrderStatus.PENDING;
    }

    public void markFilled() {
        this.orderStatus = OrderStatus.FILLED;
    }

    public void markCanceled() {
        this.orderStatus = OrderStatus.CANCELED;
    }

}
