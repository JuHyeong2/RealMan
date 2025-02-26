package com.example.demo.member.controller;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.common.util.EmailCertificationUtil;
import com.example.demo.member.model.exception.MemberException;
import com.example.demo.member.model.service.MemberService;
import com.example.demo.member.model.vo.Member;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService mService;
    private final JavaMailSender mailSender;
    private final BCryptPasswordEncoder bcrypt;

    @GetMapping("/findMyId")
    public String findMyId() {
        return "/findMyId";
    }

    @GetMapping("/findMyPwd")
    public String findMyPwd() {
        return "/findMyPwd";
    }

    @GetMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(@RequestParam("email") String email) {
        String random = "";
        int emailChecked = mService.checkEmail(email);
        if (emailChecked == 1) {
        } else {
            throw new MemberException("해당 이메일 주소로 가입된 회원이 없습니다.");
        }
        return random;
    }
  
    @GetMapping("/findId")
    @ResponseBody
    public String findId(@RequestParam("email") String email) {
        return mService.findId(email);
    }
    
    @PostMapping("/getTempPwd")
    @ResponseBody
    public String getTempPwd(@RequestParam("memberId") String memberId) {
        return "";
    }

    // 회원가입 페이지로 이동
    @GetMapping("/signup")
    public String showSignupForm() {
        return "member/signup"; 
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute Member member, Model model) {
        try {
            if (member.getMemberBirth() == 0) {
                throw new IllegalArgumentException("생년월일을 입력해주세요.");
            }

            mService.signup(member);
            model.addAttribute("message", "회원가입 성공");
            return "redirect:/member/signin";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "회원가입 중 오류가 발생했습니다: " + e.getMessage());
            return "signup";
        }
    }

    //  로그인 페이지로 이동
    @GetMapping("/signin")
    public String signin() {
        return "member/signin";
    }

    // 로그인 처리
    @PostMapping("/signin")
    public String processSignin(@RequestParam("memberEmail") String memberEmail,
                                @RequestParam("memberPwd") String memberPwd,
                                Model model) {
        Member member = mService.login(memberEmail, memberPwd);
        if (member != null) {
            return "redirect:/home";
        } else {
            model.addAttribute("errorMessage", "이메일 또는 비밀번호가 잘못되었습니다.");
            return "member/signin";
        }
    }
}
