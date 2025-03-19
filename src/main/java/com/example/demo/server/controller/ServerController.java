package com.example.demo.server.controller;


import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.member.model.service.MemberService;
import com.example.demo.member.model.vo.Member;
import com.example.demo.server.model.exception.ServerException;
import com.example.demo.server.model.service.ServerService;
import com.example.demo.server.model.vo.Server;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/server")
public class ServerController {
    private final ServerService sService;
    private final MemberService mService;

    @GetMapping("/server")
    public String server(Member m ,@PathVariable("no") int no,
                         Model model, HttpSession session){
        Member loginMember = (Member) session.getAttribute("loginMember");
        m.setMemberNo(loginMember.getMemberNo());
        ArrayList<Server> serverList = new ArrayList<Server>(sService.serverList(loginMember.getMemberNo()));
        model.addAttribute("no", no);
        model.addAttribute("serverList", serverList);

        return "/common/sidebar";
    }
    
    @GetMapping("/inviteList")
    @ResponseBody
    public ArrayList<Member> inviteList(HttpSession session){
    	Member loginMember = (Member) session.getAttribute("loginMember");
		ArrayList<Member> inviteList = sService.selectInviteList(loginMember.getMemberNo());
    	return null;
    }
   //회원 초대
    @PostMapping("/serverMember")
    @ResponseBody
    public int inviteMember(@RequestBody HashMap<String, Integer> map, 
    		HttpSession session) {
    	Member loginMember = (Member) session.getAttribute("loginMember");
    	int result = 0;
    	//is_admin 확인
    	HashMap<String, Integer> map2 = new HashMap<String, Integer>();
    	map2.put("memberNo", loginMember.getMemberNo());
    	map2.put("serverNo", map.get("serverNo"));
    	String isAdmin = sService.checkIsAdmin(map2);
    	if(isAdmin.equals("Y")) {
    		result = sService.inviteMember(map);
    	}else {
    		throw new RuntimeException("not admin");
    	}
    	
    	return result;
    }
    
    //회원 강퇴
    @DeleteMapping("/serverMember")
    @ResponseBody
    public int ejectMember() {
    	//필요한 정보들 :
    	//1. 서버넘버
    	//2. loginMember가 해당 서버의 is_admin인지
    	
    	return 0;
    }
    
    







}
