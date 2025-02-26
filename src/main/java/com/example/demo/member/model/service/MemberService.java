package com.example.demo.member.model.service;

import org.springframework.stereotype.Service;

import com.example.demo.member.model.mapper.MemberMapper;
import com.example.demo.member.model.vo.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberMapper mapper;
	
	//이메일 중복확인
	public int checkEmail(String email) { 
		return mapper.checkEmail(email);
	}

	public String findId(String email) {
		return mapper.findId(email);
	}

	public int confirmIdEmail(Member m) {
		return mapper.confirmIdEmail(m);
	}

	public int resetPwd(Member m) {
		return mapper.resetPwd(m);
	}
}
