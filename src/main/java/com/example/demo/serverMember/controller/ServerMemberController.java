package com.example.demo.serverMember.controller;


import com.example.demo.serverMember.model.service.ServerMemberService;
import com.example.demo.serverMember.model.vo.ServerMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor

public class ServerMemberController {
    private final ServerMemberService smService;

    @GetMapping("/list")
    public String serverMemberList(@RequestParam("serverNo") int serverNo, Model model) {
        ArrayList<ServerMember> serverMember =smService.serverMemberList(serverNo);
        model.addAttribute("serverMember", serverMember);
        return "serverMember";
    }

}
