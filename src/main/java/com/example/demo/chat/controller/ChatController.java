package com.example.demo.chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.chat.model.service.ChatService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

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
		model.addAttribute("ip", ip);
		return "/chat/chatting";
	}
	
	
}
