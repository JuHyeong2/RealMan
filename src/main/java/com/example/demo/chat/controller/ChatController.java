package com.example.demo.chat.controller;

import com.example.demo.chat.model.vo.Chat;
import com.example.demo.member.model.vo.Member;
import com.example.demo.server.model.vo.Server;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.chat.model.service.ChatService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
	private final ChatService cService;
	
	@GetMapping("main")
	public String mainView(HttpServletRequest request, Model model) {
		String ip = request.getHeader("X-Forwarded-For");
	    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    
		System.out.println(ip);
		
		Server server = new Server();
		server.setServerNo(1);
		
		model.addAttribute("ip", ip);
		model.addAttribute("server", server);
		return "chat/chatting";
	}


	@GetMapping("/chatting/{no}")
	public String chatting(@PathVariable("no") int no, Model model, HttpSession session) {
		Member loginMember = (Member)session.getAttribute("loginMember");

		model.addAttribute("no", no);

		ArrayList<Chat> voiceChannel= cService.chattingSidebar("V");
		model.addAttribute("voiceChannel", voiceChannel);

		ArrayList<Chat> chatChannel= cService.chattingSidebar("T");
		model.addAttribute("chatChannel", chatChannel);

		return "redirect:/chatting/" + no;

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
// chattingSidebar 할떄 쓸것
//	public String chattingSidebar(HttpServletRequest request, Model model) {
//		ArrayList<Chat> voiceChannel= cService.chattingSidebar("V");
//		ArrayList<Chat> chatChannel= cService.chattingSidebar("T");
//		model.addAttribute("chatList", voiceChannel);
//		model.addAttribute("chatList", chatChannel);
//		return "chattingSidebar";
//	}
	
}
