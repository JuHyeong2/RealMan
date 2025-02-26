package com.example.demo.server.controller;


import com.example.demo.member.model.service.MemberService;
import com.example.demo.member.model.vo.Member;
import com.example.demo.server.model.service.ServerService;
import com.example.demo.server.model.vo.Server;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/server")

public class ServerController {
    private final ServerService sService;
    private final MemberService mService;



    @GetMapping("/server/{no}")
    public String server(Member m ,@PathVariable("no") int no,
                         Model model, HttpSession session){
        Member loginMember = (Member) session.getAttribute("loginMember");
        m.setMemberNo(loginMember.getMemberNo());
        model.addAttribute("no", no);

        ArrayList<Server> serverList = new ArrayList<Server>(sService.serverList(loginMember.getMemberNo()));
        model.addAttribute("serverList", serverList);

        return "redirect:/server/" + no;
    }










}
