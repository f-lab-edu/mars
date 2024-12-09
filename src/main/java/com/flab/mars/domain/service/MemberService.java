package com.flab.mars.domain.service;


import com.flab.mars.db.entity.Member;
import com.flab.mars.db.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long processNewMember(Member member) {
        memberRepository.save(member);
        return member.getId();
    }
}
