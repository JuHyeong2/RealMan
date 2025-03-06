package com.example.demo;


import java.util.ArrayList;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.ArrayList;

import com.example.demo.member.model.vo.Member;
import com.example.demo.server.model.service.ServerService;
import com.example.demo.server.model.vo.Server;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class HomeController {
	private final ServerService sService;



	@GetMapping("home")
	public String home() {
		return "index";
	}
	

	@GetMapping("/main")
	public String mainPage(Model model, HttpSession session) {
		Member m = (Member) session.getAttribute("loginMember");
		System.out.println(m.toString());
		ArrayList<Server> selectServerList = sService.selectServerList(m);
		if(selectServerList != null || !selectServerList.isEmpty()) {
			model.addAttribute("selectServerList", selectServerList);
		}
		return "/main/main";
	}

	
}
