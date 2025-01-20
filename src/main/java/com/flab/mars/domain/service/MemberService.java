package com.flab.mars.domain.service;


import com.flab.mars.client.KISClient;
import com.flab.mars.client.KISConfig;
import com.flab.mars.db.entity.MemberEntity;
import com.flab.mars.db.repository.MemberRepository;
import com.flab.mars.domain.vo.CreateMember;
import com.flab.mars.domain.vo.MemberInfoVO;
import com.flab.mars.domain.vo.TokenInfo;
import com.flab.mars.domain.vo.response.AccessTokenVO;
import com.flab.mars.exception.BadWebClientRequestException;
import com.flab.mars.support.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final KISClient kisClient;

    private final KISConfig kisConfig;


    public AccessTokenVO getAccessToken(TokenInfo tokenInfo) {

        try {
            String accessToken = kisClient.getAccessToken(tokenInfo.getAppKey(), tokenInfo.getAppSecret(), kisConfig.getGrantType());
            tokenInfo.setAccessToken(accessToken);
            return new AccessTokenVO(true, "AccessToken 발급 성공", HttpStatus.OK, accessToken);
        } catch (BadWebClientRequestException e) {
            log.error("AccessToken 발급 실패 : {}", e.getErrorDescription());
            return new AccessTokenVO(false, e.getErrorDescription(), e.getStatusCode(), null);
        }
    }

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
    public void deleteById(Long id) {
        MemberEntity memberEntity = findMemberById(id);
        memberRepository.delete(memberEntity);
    }
    public MemberEntity findMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new IllegalStateException("회원을 조회할 수 없습니다."));
    }


    public boolean login(String email, String pw, TokenInfo tokenInfo, HttpSession session) {

        Optional<MemberEntity> memberOptionl = memberRepository.findByEmail(email);
        if(memberOptionl.isEmpty()) return false;

        MemberEntity memberEntity = memberOptionl.get();

        // 비밀번호 검사
        if(!passwordEncoder.matches(pw, memberEntity.getPw())) return false;

        // 로그인 처리
        SessionUtil.setSessionValue(session, SessionUtil.ROLE, MemberInfoVO.createMemberInfoVO(memberEntity, tokenInfo));

        return true;
    }
}
