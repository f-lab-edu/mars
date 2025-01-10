package com.flab.mars.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class MemberEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String pw;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    private LocalDateTime lastAccessTime;

    private LocalDateTime joinTime;



    public MemberEntity(String name, String email, String pw ) {
        this.name = name;
        this.email = email;
        this.pw = pw;
        this.status = MemberStatus.ACTIVE;
    }

    public MemberEntity updateMember(String name, String pw, PasswordEncoder passwordEncoder ){
        if(StringUtils.hasText(name)) this.name = name;
        if(StringUtils.hasText(pw)) this.pw = passwordEncoder.encode(pw);
        return this;
    }
}