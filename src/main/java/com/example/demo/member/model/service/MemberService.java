package com.example.demo.member.model.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.member.model.mapper.MemberMapper;
import com.example.demo.member.model.vo.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberMapper mapper;
	private final BCryptPasswordEncoder bcrypt;

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
//    public Member login(String memberId, String memberPwd) {
//        Member member = mapper.login(memberId); // 비밀번호는 따로 비교
//        if (member != null && bcrypt.matches(memberPwd, member.getMemberPwd())) {
//            return member;
//        }
//        return null;
//    }
	public Member login(String memberId, String memberPwd) {
		Member member = mapper.login(memberId);
		if (member != null && bcrypt.matches(memberPwd, member.getMemberPwd())) {
			return member;
		}
		return null;
	}

	// 회원가입 처리 (성공 여부 반환)
	public int insertMember(Member member) {
		if (member.getMemberBirth() == 0) {
			throw new IllegalArgumentException("생년월일은 필수 입력 값입니다.");
		}

		// 비밀번호 암호화 후 저장
		member.setMemberPwd(bcrypt.encode(member.getMemberPwd()));

		// 회원 정보 DB 저장 후 결과 반환
		return mapper.insertMember(member);
	}

	// 친구 목록 가져오기(번호만)
	public ArrayList<Integer> selectFriendNumbers(Member loginMember) {
		return mapper.selectFriendNumbers(loginMember);
	}

	// 내가 보낸 친구 요청 목록 가져오기(번호만)
	public ArrayList<Integer> selectRequestSent(int memberNo) {
		return mapper.selectRequestSent(memberNo);
	}
	
	// 내가 받은 친구 요청 목록 가져오기(번호만)
	public ArrayList<Integer> selectRequestReceived(int memberNo) {
		return mapper.selectRequestReceived(memberNo);
	}

	// 친구 목록 조회
	public ArrayList<Member> selectFriends(ArrayList<Integer> friendNumberList) {
		return mapper.selectFriends(friendNumberList);
	}

	// 친구 삭제, 거절, 요청 취소
	public int deleteFriend(HashMap<String, Integer> map) {
		return mapper.deleteFriend(map);
	}
	
	// 친구 수락
	public int approveRequest(HashMap<String, Integer> map) {
		return mapper.approveRequest(map);
	}
	
	//회원 찾기 (친구 추가)
	public ArrayList<Member> findMember(Map<String, String> searchMap) {
		return mapper.findMember(searchMap);
	}

}
