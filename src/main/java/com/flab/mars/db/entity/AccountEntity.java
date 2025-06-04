package com.flab.mars.db.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    private String accountPrefix;  // 계좌번호 앞 8자리
    private String accountSuffix;  // 계좌번호 뒤 2자리

    private boolean defaultFlag;

}
