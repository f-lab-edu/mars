package com.flab.mars.db.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "interest_stock")
public class InterestStockEntity {

    @Id
    @GeneratedValue
    @Column (name = "interest_stock_id")
    private Long id; // 관심 주식의 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_info_id")
    private StockInfoEntity stockInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
