package com.example.demo.common.util;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailCertificationUtil {
	private final JavaMailSender mailSender;
	
	
	public void sendEmail(String email, String random) {
		String title = "[REALMAN] 계정 확인 이메일";
		String body = "<div><h1>인증번호</h1><div>";
		body += "<div>"+random+"</div>";
		
	}
}
