package com.flab.mars.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.mars.api.dto.request.CreateMemberRequest;
import com.flab.mars.api.dto.request.UpdateMemberRequest;
import com.flab.mars.db.repository.MemberRepository;
import com.flab.mars.domain.service.MemberService;
import com.flab.mars.domain.vo.CreateMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberAPIController.class)
@WithMockUser(roles = "USER") // 인증된 사용자
class MemberAPIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MemberRepository memberRepository;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private MemberService memberService;


    @Test
    @DisplayName("회원 가입을 진행한다")
    void createMemberTest() throws Exception {
        // given
        CreateMemberRequest createMemberRequest = CreateMemberRequest
                .builder()
                .name("John Doe")
                .email("test@example.com")
                .pw("password123#A")
                .passwordCheck("password123#A")
                .build();


        // when, then
        mockMvc.perform(post("/api/members")
                .content(objectMapper.writeValueAsString(createMemberRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("입력 값이 누락되면 400 에러를 반환한다.")
    void updateMemberBadRequest() throws Exception {
        // Given
        Long memberId = 1L;
        UpdateMemberRequest request = new UpdateMemberRequest();
        request.setName(""); // 이름 누락
        request.setPw("password123");
        request.setEmail("invalid-email-format"); // 잘못된 이메일 형식

        // When & Then
        mockMvc.perform(put("/api/members/{id}", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf())
                )
                .andExpect(status().isBadRequest());

    }

    @DisplayName("회원 이름 또는 비밀번호를 수정한다.")
    @Test
    void updateMember() throws Exception {
        // given
        CreateMember createMemberRequest = CreateMember
                .builder()
                .name("John Doe")
                .email("test@example.com")
                .pw("password123#A")
                .build();

        Long memberId = memberService.processNewMember(createMemberRequest);
        System.out.println(memberId);


        UpdateMemberRequest updateMemberRequest = UpdateMemberRequest
                .builder()
                .name("Updated Name")
                .pw("newpassword123#A")
                .build();

        Mockito.doNothing().when(memberService).updateMember(memberId, updateMemberRequest.getName(), updateMemberRequest.getPw());


        // when, then
        mockMvc.perform(put("/api/members/{id}", memberId)
                        .content(objectMapper.writeValueAsString(updateMemberRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(memberId))
                .andExpect(jsonPath("$.name").value(updateMemberRequest.getName()))
                .andExpect(jsonPath("$.message").value("회원 가입 성공"));
    }

    @Test
    void deleteMember_success() throws Exception {
        // given
        CreateMember createMemberRequest = CreateMember
                .builder()
                .name("John Doe")
                .email("test@example.com")
                .pw("password123#A")
                .build();

        Long memberId = memberService.processNewMember(createMemberRequest);
        // when & then
        mockMvc.perform(delete("/api/members/{id}", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isNoContent());

        Mockito.verify(memberService).deleteById(memberId);
    }

}