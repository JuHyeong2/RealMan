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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.member.model.service.MemberService;
import com.example.demo.member.model.vo.Member;
import com.example.demo.member.model.vo.ProfileImage;

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
		
		// 친구목록
		ArrayList<Integer> friendNumberList = mService.selectFriendNumbers(loginMember);
		ArrayList<Member> list = friendNumberList.isEmpty()? null : mService.selectMembers(friendNumberList);
		for(int i = 0; i < list.size(); i++) {
			ProfileImage img = mService.selectImage(list.get(i).getMemberNo());
			if(img != null) {
				list.get(i).setImageUrl(img.getImgRename());
			}
		}
		// 내가 보낸 요청 목록
		ArrayList<Integer> sentRequestList = mService.selectRequestSent(loginMember.getMemberNo());
		ArrayList<Member> wlist = sentRequestList.isEmpty() ? null : mService.selectMembers(sentRequestList);
		// 나한테 온 요청 목록
		ArrayList<Integer> receivedRequestList = mService.selectRequestReceived(loginMember.getMemberNo());
		ArrayList<Member> rlist = receivedRequestList.isEmpty() ? null : mService.selectMembers(receivedRequestList);
		
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
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("myMemberNo", loginMember.getMemberNo());
		map.put("friendMemberNo", friendMemberNo);
		int result = mService.deleteFriend(map);
		System.out.println("deleteFriend result : " + result);
		return result;
	}

	// 친구 요청 (friend 테이블에 행 추가)
	@PostMapping("/friend")
	public int requestFriend(@RequestBody HashMap<String, Integer> map2,
			HttpSession session) {
		int result = 0;
		
		int friendMemberNo = map2.get("fnm");
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		//이미 친구인지 확인
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("myMemberNo", loginMember.getMemberNo());
		map.put("friendMemberNo", friendMemberNo);
		HashMap<String, String> friendCheck = mService.friendCheck(map);

		//친구 요청
		if(friendCheck == null) {
			result = mService.requestFriend(map);
		}else{
			//friend_status = 'W' 라면 이미 보낸 요청이라 알림
			//friend_status = 'A' 라면 이미 친구라고 알림
		}
		
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
