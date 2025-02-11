package com.example.demo.member.model.service;

import org.springframework.stereotype.Service;

import com.example.demo.member.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberMapper mapper;
}
