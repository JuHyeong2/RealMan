package com.example.demo.common.util;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailCertificationUtil {
	private final JavaMailSender mailSender;
	
	
	public void sendEmail(String email, String random) throws MessagingException, MailException {
		String title = "[REALMAN] 계정 확인 이메일";
		String body = "<div><h1>인증번호</h1><div>";
		body += "<div>"+random+"</div>";
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
		
		mimeMessageHelper.setTo(email);
		mimeMessageHelper.setSubject(title);
		mimeMessageHelper.setText(body, true);
		mailSender.send(mimeMessage);
	}
}
