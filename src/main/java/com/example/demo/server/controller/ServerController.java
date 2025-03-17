package com.example.demo.server.controller;


import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.member.model.service.MemberService;
import com.example.demo.member.model.vo.Member;
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

   //회원 초대
    @PostMapping("/server/member")
    @ResponseBody
    public int inviteMember() {
    	//필요한 정보들 :
    	//1. 서버넘버
    	//2. loginMember가 해당 서버의 is_admin인지
    	
    	return 0;
    }
    
    //회원 강퇴
    @DeleteMapping("/server/member")
    @ResponseBody
    public int removeMember() {
    	//필요한 정보들 :
    	//1. 서버넘버
    	//2. loginMember가 해당 서버의 is_admin인지
    	
    	return 0;
    }
    
    







}
