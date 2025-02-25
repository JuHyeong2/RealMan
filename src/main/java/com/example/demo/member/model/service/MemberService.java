package com.example.demo.member.model.service;

import org.springframework.stereotype.Service;

import com.example.demo.member.model.mapper.MemberMapper;

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
}
