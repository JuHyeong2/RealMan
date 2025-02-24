package com.example.demo.member.controller;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.member.model.exception.MemberException;
import com.example.demo.member.model.service.MemberService;

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
	
	//(아이디찾기, 비밀번호찾기)이메일 보내기
	@GetMapping("/sendEmail")
	@ResponseBody
	public String sendEmail(@RequestParam("eamil") String email) {
		String random = "";
		//1. 가입된 이메일인지 확인
		int emailchecked = mService.checkEmail(email);
		if (emailchecked == 1) {
			//2. 이메일 전송
			//mailSender.createMimeMessage()로 MimeMessage객체 만들고
			//제목, 내용(html 형식) 작성
			//random 생성.
			//MimeMessageHelper() 객체 생성
			//setTo, setSubject, setText(body, true)로 이메일 정보 담아서
			//mailSender.send()
		}else {
		 throw new MemberException("해당 이메일 주소로 가입된 회원이 없습니다.");
		}
		
		return random;
	}
	
	@GetMapping("/findId")
	@ResponseBody
	public String findId(@RequestParam("email") String email) {
		String memberId = mService.findId(email);
		return memberId;
	}
	
	@PostMapping("/getTempPwd")
	@ResponseBody
	public String getTempPwd(@RequestParam("memberId") String memberId) {
		return "";
	}
	
}
