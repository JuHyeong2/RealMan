package com.example.demo.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.member.model.vo.Member;

import com.example.demo.member.model.vo.Member;

@Mapper
public interface MemberMapper {

    // 이메일 중복 확인
    int checkEmail(String email);

    // 아이디 이메일 일치여부 확인
    int confirmIdEmail(Member m);

    // 비밀번호 재설정
    int resetPwd(Member m);

    // 이메일로 아이디 찾기
    String findId(String email);

    // 로그인 처리
    Member login(@Param("memberEmail") String memberEmail, @Param("memberPwd") String memberPwd);

    // 회원가입 처리
    void insertMember(Member member);
}
