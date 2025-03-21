package com.example.demo.member.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.member.model.vo.Member;
import com.example.demo.member.model.vo.ProfileImage;

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
    Member login(@Param("memberId") String memberId);

    // 회원가입 처리
    int insertMember(Member member);

    // 아이디 중복 체크
    int checkMemberId(String memberId);

    // 닉네임 중복 체크
    int checkMemberNickname(String memberNickname);

    // 전화번호 중복 체크
    int checkMemberPhone(String memberPhone);

    // 이메일 중복 체크
    int checkMemberEmail(String memberEmail);
    
	//친구 목록 가져오기(번호만)
    ArrayList<Integer> selectFriendNumbers(Member loginMember);

    //내가 보낸 친구 요청 목록 가져오기(번호만)
    ArrayList<Integer> selectRequestSent(int memberNo);

    //내가 받은 친구 요청 목록 가져오기(번호만)
    ArrayList<Integer> selectRequestReceived(int memberNo);

    //친구 목록 조회
    ArrayList<Member> selectFriends(ArrayList<Integer> friendNumberList);

    //친구 삭제, 거절, 요청 취소
    int deleteFriend(HashMap<String, Integer> map);

    //친구 수락
	int approveRequest(HashMap<String, Integer> map);
	
	//회원 찾기 (친구 추가)
	ArrayList<Member> findMember(Map<String, String> searchMap);
	
	//친구관계 확인
	HashMap<String, String> friendCheck(HashMap<String, Integer> map);
	
	//친구 요청
	int requestFriend(HashMap<String, Integer> map);
	
	//차단 여부 확인
	int blockCheck(HashMap<String, Integer> map);

	//회원 차단
	int blockMember(HashMap<String, Integer> map);
	
	//회원 정보 수정
	int editMemberInfo(HashMap<String, String> map);

	Member selectMember(int memberNo);

	ProfileImage getProfileImage(int mcdNo);

	int insertProfileImage(ProfileImage profileImage);

	int updateProfileImage(ProfileImage profileImage);

	ProfileImage selectImage(int memberNo);

	ArrayList<Member> selectMembers(ArrayList<Integer> memberNumberList);


    int getMemberNo(String memberId);

	Member selectMemberNo(String sender);
}
