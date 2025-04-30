package com.flab.mars.domain.component;

import com.flab.mars.db.entity.MemberEntity;
import com.flab.mars.db.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidator {
    private final MemberRepository memberRepository;

    public MemberEntity validateExist(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 ID: " + memberId));
    }
}
