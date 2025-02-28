package com.example.demo.member.model.service;

import java.util.ArrayList;
import java.util.HashMap;

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

    // 이메일 아이디 일치 여부 확인
    public int confirmIdEmail(Member m) {
        return mapper.confirmIdEmail(m);
    }

    // 비밀번호 재설정
    public int resetPwd(Member m) {
        return mapper.resetPwd(m);
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
    }
    
    //친구 목록 가져오기(번호만)
	public ArrayList<Integer> selectFriendNumbers(Member loginMember) {
		return mapper.selectFriendNumbers(loginMember);
	}
	
	//친구 목록 조회
	public ArrayList<Member> selectFriends(ArrayList<Integer> friendNumberList) {
		return mapper.selectFriends(friendNumberList);
	}
}
