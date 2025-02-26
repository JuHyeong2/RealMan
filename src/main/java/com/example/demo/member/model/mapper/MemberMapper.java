package com.example.demo.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.member.model.vo.Member;

@Mapper
public interface MemberMapper {

	int checkEmail(String email);

	String findId(String email);
	
	int confirmIdEmail(Member m);

	int resetPwd(Member m);

}
