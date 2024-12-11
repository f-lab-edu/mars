package com.flab.mars.domain.service;


import com.flab.mars.db.entity.MemberEntity;
import com.flab.mars.db.repository.MemberRepository;
import com.flab.mars.domain.vo.CreateMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 생성
     * @param memberVO
     * @return
     */
    @Transactional
    public Long processNewMember(CreateMember memberVO) {
        // 이메일 중복 체크
        long cnt = memberRepository.countByEmail(memberVO.getEmail());
        if(cnt > 0) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

        MemberEntity memberEntity = new MemberEntity(memberVO.getName(), memberVO.getEmail(), passwordEncoder.encode(memberVO.getPw()));
        memberRepository.save(memberEntity);

        return memberEntity.getId();
    }

    /**
     *  name , pw 변경
     * @param id
     * @param name
     * @param pw
     */

    @Transactional
    public void updateMember(Long id, String name, String pw) {
        MemberEntity memberById = findMemberById(id);
        memberById.updateMember(name, pw, passwordEncoder);

    }

    @Transactional
    public boolean deleteById(Long id) {
        MemberEntity memberEntity = findMemberById(id);
        memberRepository.delete(memberEntity);
        return true;
    }

    public MemberEntity findMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new IllegalStateException("회원을 조회할 수 없습니다."));
    }




}
