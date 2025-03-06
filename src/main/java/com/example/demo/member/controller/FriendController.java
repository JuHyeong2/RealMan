package com.example.demo.member.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FriendController {
	private final MemberService mService;
	
	//친구 삭제, 거절 (friend 테이블에 행 제거)
	@DeleteMapping("/friend")
	public int delteFriend() {
		System.out.println();
		int result = 0;
		System.out.println("deleteFriend result : "+result);
		return result;
	}
	
	//친구 요청 (friend 테이블에 행 추가)
	@PostMapping("/friend")
	public int requestFriend() {
		System.out.println();
		int result = 0;
		System.out.println("requestFriend result : "+result);
		return result;
	}
	
	//친구 수락 (friend_status 바꿈 w -> a)
	@PutMapping("/friend")
	public int approveRequest() {
		System.out.println();
		int result = 0;
		System.out.println("requestFriend result : "+result);
		return result;
	}
}
