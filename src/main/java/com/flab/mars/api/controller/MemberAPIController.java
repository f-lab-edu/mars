package com.flab.mars.api.controller;

import com.flab.mars.api.dto.request.CreateMemberRequest;
import com.flab.mars.api.dto.request.UpdateMemberRequest;
import com.flab.mars.api.dto.response.CreateMemberResponse;
import com.flab.mars.api.dto.response.UpdateMemberResponse;
import com.flab.mars.domain.service.MemberService;
import com.flab.mars.domain.vo.CreateMember;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@Slf4j
public class MemberAPIController {

    private final MemberService memberService;


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
        if(memberService.deleteByIdAndReturnCount(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
