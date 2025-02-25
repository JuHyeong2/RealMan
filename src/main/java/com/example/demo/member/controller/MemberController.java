package com.example.demo.member.controller;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.common.util.EmailCertificationUtil;
import com.example.demo.member.model.exception.MemberException;
import com.example.demo.member.model.service.MemberService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
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
		System.out.println("user email : "+email);
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
	
	@PostMapping("/updatePwd")
	@ResponseBody
	public String getTempPwd(@RequestParam("memberId") String memberId) {
		return "";
	}
	
}
