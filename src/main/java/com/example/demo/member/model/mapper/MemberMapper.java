package com.example.demo.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

	int checkEmail(String email);

}
