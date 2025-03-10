package com.example.demo.member.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.member.model.service.MemberService;
import com.example.demo.member.model.vo.Member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FriendController {
	private final MemberService mService;
	
	//친구 목록 가져오기
	@GetMapping("/friend")
	public Map<String, ArrayList<Member>> friends(HttpSession session) {
		Member loginMember = (Member) session.getAttribute("loginMember");
		Map<String, ArrayList<Member>> map = new HashMap<String, ArrayList<Member>>();
		System.out.println("loginMember : "+loginMember.getMemberId());
		
		// 친구목록
		ArrayList<Integer> friendNumberList = mService.selectFriendNumbers(loginMember);
		ArrayList<Member> list = mService.selectFriends(friendNumberList);
		// 내가 보낸 요청 목록
		ArrayList<Integer> sentRequestList = mService.selectRequestSent(loginMember.getMemberNo());
		ArrayList<Member> wlist = sentRequestList.isEmpty() ? null : mService.selectFriends(sentRequestList);
		// 나한테 온 요청 목록
		ArrayList<Integer> receivedRequestList = mService.selectRequestReceived(loginMember.getMemberNo());
		ArrayList<Member> rlist = receivedRequestList.isEmpty() ? null : mService.selectFriends(receivedRequestList);
		
		System.out.println("list : "+list);
		System.out.println("wlist : "+wlist);
		System.out.println("lrist : "+rlist);
		map.put("list", list);
		map.put("wlist", wlist);
		map.put("rlist", rlist);

		return map;
	}

	// 친구 삭제, 거절, 요청 취소 (friend 테이블에 행 제거)
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
