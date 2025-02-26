package com.example.demo.member.controller;

import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.common.util.EmailCertificationUtil;
import com.example.demo.member.model.service.MemberService;
import com.example.demo.member.model.vo.Member;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@SessionAttributes("loginUser")
public class MemberController {
	private final MemberService mService;
	private final BCryptPasswordEncoder bcrypt;
	private final EmailCertificationUtil emailUtil;
	
	@GetMapping("/findMyId")
	public String findMyId() {
		return "/findMyId";
	}
	
	@GetMapping("/findMyPwd")
	public String findMyPwd() {
		return "/findMyPwd";
	}
	
	//(아이디찾기, 비밀번호찾기)이메일 보내기
	@GetMapping("/sendEmail")
	@ResponseBody
	public String sendEmail(@RequestParam("email") String email) {
		System.out.println("sendEmail : "+email);
		String random = "";
		//1. 가입된 이메일인지 확인
		int emailchecked = mService.checkEmail(email);
		
		if (emailchecked == 1) {
			//2. 코드 생성
			for (int i=0;i<6;i++) {
				String pool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
				int r = (int)(Math.random()*(pool.length()+1));
				char c = pool.charAt(r);
				random += c;
			}
			//3. 이메일 전송
			try {
				emailUtil.sendEmail(email, random);
				return random;
			} catch (MailException e) {
				return "MailException";
			} catch (MessagingException e) {
				return "MessagingException";
			}
		}else {
			return "EmailNotFound";
		}
	}
	
	@GetMapping("/findId")
	@ResponseBody
	public String findId(@RequestParam("email") String email) {
		String memberId = mService.findId(email);
		return memberId;
	}
	
	@PostMapping("/resetPwd")
	@ResponseBody
	public String resetPwd(@ModelAttribute Member m, 
			@RequestParam("newPwd") String newPwd,
			Model model) {
		System.out.println("memberId : "+m.getMemberId());
		System.out.println("memberEmail : "+m.getMemberEmail());
		System.out.println("newPwd : "+newPwd);
		
		//1. 사용자가 입력한 아이디와 - 이메일이 일치하는지 조회
		int check = mService.confirmIdEmail(m);
		System.out.println("confirmIdEmail : "+check);
		
		if (check == 1) {
			m.setMemberPwd(bcrypt.encode(newPwd));
			
			int result = mService.resetPwd(m);
			System.out.println("resetPwd : "+result);
			
			if (result == 1) {
				//model.addAttribute("loginUser", m);
				return "success";
			}
		}else {
			return "MemberNotFound";
		}
		return "?";
	}
	
}
