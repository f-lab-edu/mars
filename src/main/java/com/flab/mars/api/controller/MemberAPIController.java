package com.flab.mars.api.controller;

import com.flab.mars.api.dto.request.CreateMemberRequest;
import com.flab.mars.api.dto.request.LoginRequestDto;
import com.flab.mars.api.dto.request.UpdateMemberRequest;
import com.flab.mars.api.dto.response.CreateMemberResponse;
import com.flab.mars.api.dto.response.ResultAPIDto;
import com.flab.mars.api.dto.response.UpdateMemberResponse;
import com.flab.mars.domain.service.MemberService;
import com.flab.mars.domain.vo.CreateMember;
import com.flab.mars.domain.vo.TokenInfo;
import com.flab.mars.domain.vo.response.AccessTokenResponseVO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Slf4j
public class MemberAPIController {

    private final MemberService memberService;


    @PostMapping("/login")
    public ResponseEntity<ResultAPIDto<String>> login(@RequestBody @Valid LoginRequestDto request, HttpSession session) {

        TokenInfo tokenInfo = new TokenInfo(request.getAppKey(), request.getAppSecret());
        AccessTokenResponseVO accessToken = memberService.getAccessToken(tokenInfo);

        // 토큰 발급 실패시
        if (accessToken.getStatusCode() != null && accessToken.getStatusCode().isError()) {
            return ResponseEntity.badRequest().body(ResultAPIDto.res(HttpStatus.BAD_REQUEST, "토큰 발급 실패", accessToken.getMessage()));
        }

        if(!memberService.login(request.getEmail(), request.getPw(), tokenInfo, session)) {
            return ResponseEntity.badRequest().body(ResultAPIDto.res(HttpStatus.BAD_REQUEST, "로그인 실패", "Failure"));
        }

        return ResponseEntity.ok(ResultAPIDto.res(HttpStatus.OK, "로그인 성공", "Success"));
    }


    /**
     * 사용자 회원가입 기능
     * @param request
     * @return
     */
    @PostMapping
    public CreateMemberResponse createMember(@RequestBody  @Valid CreateMemberRequest request){

        log.info("POST /api/members 요청 - 사용자 생성: {}", request.getEmail());

        String name = request.getName();
        String email = request.getEmail();
        String pw = request.getPw();

        // dto -> vo 변환
        CreateMember createMember = CreateMember.builder()
                .name(name)
                .email(email)
                .pw(pw)
                .build();

        Long memberId = memberService.processNewMember(createMember);
        return new CreateMemberResponse(memberId, name, email, "회원 가입 성공");
    }

    /**
     * 사용자 업데이트 기능
     * @param request
     * @return
     */
    @PutMapping("/{id}")
    public UpdateMemberResponse updateMember(@PathVariable("id") Long id , @RequestBody @Valid UpdateMemberRequest request){

        memberService.updateMember(id, request.getName(), request.getPw());

        return new UpdateMemberResponse(id, request.getName(), "회원 가입 성공");
    }


    /**
     * 사용자 삭제 기능
     * @param id 회원의 고유 ID
     * @return 삭제 결과 상태
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id ){
        try {
            memberService.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
