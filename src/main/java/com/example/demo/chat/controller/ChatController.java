package com.example.demo.chat.controller;

import com.example.demo.chat.model.vo.Chat;
import com.example.demo.chat.model.vo.ChatMessage;
import com.example.demo.member.model.vo.Member;
import com.example.demo.server.model.service.ServerService;
import com.example.demo.server.model.vo.Server;

import jakarta.servlet.http.HttpSession;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.chat.model.service.ChatService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class ChatController {
	private final ChatService cService;
	private final ServerService sService;
	

	private final SimpMessagingTemplate messagingTemplate;

	@GetMapping("main")
	public String mainView(HttpServletRequest request, Model model, HttpSession session) {
//		String ip = request.getHeader("X-Forwarded-For");
//	    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
//	        ip = request.getHeader("Proxy-Client-IP");
//	    }
//	    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
//	        ip = request.getHeader("WL-Proxy-Client-IP");
//	    }
//	    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
//	        ip = request.getHeader("HTTP_CLIENT_IP");
//	    }
//	    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
//	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//	    }
//	    if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
//	        ip = request.getRemoteAddr();
//	    }
	    
	    
//		System.out.println(ip);
		Member m = (Member) session.getAttribute("loginMember");
		System.out.println(m.toString());
//		Server server = new Server();
//		server.setServerNo(server.getServerNo());
//
//		System.out.println(server.getServerNo());

		ArrayList<Server> selectServerList = sService.selectServerList(m);
		if(selectServerList != null || !selectServerList.isEmpty()) {
			model.addAttribute("selectServerList", selectServerList);
		}
		
//		model.addAttribute("ip", ip);
//		model.addAttribute("server", server);
		
		model.addAttribute("member", m);

		return "chat/chatting";
	}



	@GetMapping("/main/{no}")
	public String chatting(@PathVariable("no") int no, Model model, HttpSession session) {
		Member loginMember = (Member)session.getAttribute("loginMember");
		System.out.println(no);
		
		ArrayList<Server> selectServerList = sService.selectServerList(loginMember);

		if(selectServerList != null || !selectServerList.isEmpty()) {
			model.addAttribute("selectServerList", selectServerList);
		}
		
		model.addAttribute("no", no).addAttribute("member", loginMember);

		ArrayList<Chat> voiceChannel= cService.chattingSidebar("V");
		model.addAttribute("voiceChannel", voiceChannel);

		ArrayList<Chat> chatChannel= cService.chattingSidebar("T");
		model.addAttribute("chatChannel", chatChannel);

		return "chat/chatting";

	}
	
	@MessageMapping("/chat/{serverNo}")
	public void sendMessage(@DestinationVariable("serverNo") String serverNo, ChatMessage message) {
		// 특정  채팅방(roomId)에 메시지를 전송
		System.out.println("serverNo : " + serverNo);
		System.out.println("message : " + message);
		messagingTemplate.convertAndSend("/sub/chatroom/" + serverNo, message);
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
