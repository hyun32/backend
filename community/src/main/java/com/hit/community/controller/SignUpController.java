package com.hit.community.controller;

import com.hit.community.dto.MemberResponse;
import com.hit.community.entity.Member;
import com.hit.community.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class SignUpController {

    private final MemberService memberService;

    @GetMapping("/signup/{id}")
    public String signupForm(
            Model model,
            @PathVariable Long id){

        MemberResponse memberResponse = memberService.getMember(id);
        model.addAttribute("member", memberResponse);
        return "signup";
    }
}
