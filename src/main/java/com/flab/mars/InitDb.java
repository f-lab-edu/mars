package com.flab.mars;

import com.flab.mars.db.entity.MemberEntity;
import com.flab.mars.db.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 샘플 데이터 넣기
 * usreA
 */
@Component
@RequiredArgsConstructor
public class InitDb {

    @Autowired private MemberRepository memberRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {

        String encodePw = passwordEncoder.encode("password2025!");

        MemberEntity memberEntity = new MemberEntity("name1", "test@mail.com", encodePw);
        memberRepository.save(memberEntity);
    }
}
