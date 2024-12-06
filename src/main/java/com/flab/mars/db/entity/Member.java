package com.flab.mars.db.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
@Entity
@Getter
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

}
