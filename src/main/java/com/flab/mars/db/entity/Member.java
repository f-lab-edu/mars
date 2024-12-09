package com.flab.mars.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String email;

    private String pw;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    private LocalDateTime lastAccessTime;

    private LocalDateTime joinTime;



    public Member(String name, String email, String pw) {
        this.name = name;
        this.email = email;
        this.pw = pw;
        this.status = MemberStatus.ACTIVE;
    }
}