package com.flab.mars.api.controller;


import com.flab.mars.api.dto.MemberForm;
import com.flab.mars.db.entity.Member;
import com.flab.mars.domain.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //회원 가입 화면이동
    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    // 회원 가입
    @PostMapping("/members/new")
    public String createMember(@Valid MemberForm form, BindingResult result){

        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        Member member = new Member(form.getName(), form.getEmail(), form.getPw());
        memberService.processNewMember(member);

        return "redirect:/";
    }

}
