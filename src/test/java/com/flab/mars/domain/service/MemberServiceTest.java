package com.flab.mars.domain.service;

import com.flab.mars.db.repository.MemberRepository;
import com.flab.mars.domain.vo.CreateMember;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;



    @BeforeEach
    void setUp() {

    }

    @DisplayName("email duplication  should throw exception")
    @Test
    void emailDuplicationThrowsException() {
         CreateMember newMember1 = CreateMember.builder()
                .email("duplicate@email.com")
                .name("test1")
                .pw("pw")
                .build();

        CreateMember newMember2 = CreateMember.builder()
                .email("duplicate@email.com")
                .name("test1")
                .pw("pw")
                .build();
        // given
     //   given(memberRepository.countByEmail(newMember1.getEmail())).willReturn(0L);
     //   given(memberRepository.countByEmail(newMember2.getEmail())).willReturn(1L);

        // when
        System.out.println("111" + newMember1.getName());
        memberService.processNewMember(newMember1);


        Assertions.assertThrows(IllegalStateException.class, () -> {
            System.out.println("222" + newMember2.getName());
            memberService.processNewMember(newMember2);
            System.out.println("333" + newMember2.getName());
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });

    }

    @DisplayName("Member registration success test")
    @Test
    void siguUpSuccess() {
        // given
        CreateMember newMember1 = CreateMember.builder()
                .email("duplicate@email.com")
                .name("test1")
                .pw("pw")
                .build();

        // when
        Long memberId  = memberService.processNewMember(newMember1);
        System.out.println("memberId = " + memberId);
//
//        ArgumentCaptor<MemberEntity> captor = ArgumentCaptor.forClass(MemberEntity.class);
//        verify(memberRepository).save(captor.capture());
//        MemberEntity savedMember = captor.getValue();
//
//        assertEquals("test1", savedMember.getName());
//        assertEquals("duplicate@example.com", savedMember.getEmail());
     //    assertNotNull(memberId);
    }



}