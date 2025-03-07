package com.example.demo.member.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.member.model.service.MemberService;
import com.example.demo.member.model.vo.Member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FriendController {
	private final MemberService mService;

	// 친구 삭제, 거절 (friend 테이블에 행 제거)
	@DeleteMapping("/friend")
	public int deleteFriend(@RequestBody HashMap<String, Integer> map2,
			HttpSession session) {
		int friendMemberNo = map2.get("fnm");
		Member loginMember = (Member) session.getAttribute("loginMember");
		System.out.println("friendMemberNo : "+friendMemberNo);
		System.out.println("my member no : " + loginMember.getMemberNo());
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("myMemberNo", loginMember.getMemberNo());
		map.put("friendMemberNo", friendMemberNo);
		int result = mService.deleteFriend(map);
		System.out.println("deleteFriend result : " + result);
		return result;
	}

	// 친구 요청 (friend 테이블에 행 추가)
	@PostMapping("/friend")
	public int requestFriend() {
		System.out.println();
		int result = 0;
		System.out.println("requestFriend result : " + result);
		return result;
	}

	// 친구 수락 (friend_status 바꿈 w -> a)
	@PutMapping("/friend")
	public int approveRequest(@RequestBody HashMap<String, Integer> map2, 
			HttpSession session
			) {
		int friendMemberNo = map2.get("fnm");
		Member loginMember = (Member) session.getAttribute("loginMember");
		System.out.println("friendMemberNo : "+friendMemberNo);
		System.out.println("my member no : " + loginMember.getMemberNo());
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("myMemberNo", loginMember.getMemberNo());
		map.put("friendMemberNo", friendMemberNo);
		
		int result = mService.approveRequest(map);
		
		return result;
	}
}
