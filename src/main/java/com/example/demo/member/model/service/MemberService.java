package com.example.demo.member.model.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Service;

import com.example.demo.member.model.mapper.MemberMapper;
import com.example.demo.member.model.vo.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper mapper;

    // 이메일 중복 확인
    public int checkEmail(String email) {
        return mapper.checkEmail(email);
    }

    // 이메일로 아이디 찾기
    public String findId(String email) {
        return mapper.findId(email);
    }

    // 로그인 처리
    public Member login(String memberEmail, String memberPwd) {
        return mapper.login(memberEmail, memberPwd);
    }

    // 회원가입 처리
    public void signup(Member member) {
        if (member.getMemberBirth() == 0) {  
            throw new IllegalArgumentException("생년월일은 필수 입력 값입니다.");
        }

        member.setMemberStatus("A"); 
        member.setMemberIsAdmin("N"); 

        mapper.insertMember(member);
    }
}
