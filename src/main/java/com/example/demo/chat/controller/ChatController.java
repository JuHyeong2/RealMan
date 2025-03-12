package com.example.demo.chat.controller;

import com.example.demo.chat.model.vo.Channel;
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

import org.springframework.web.bind.annotation.*;


import com.example.demo.chat.model.service.ChatService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;


import java.util.ArrayList;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
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



	@GetMapping("/main/{serverNo}/{channelNo}")
	public String chatting(@PathVariable("serverNo") int serverNo, @PathVariable("channelNo") int channelNo, Model model, HttpSession session) {
		Member loginMember = (Member)session.getAttribute("loginMember");
		System.out.println(serverNo);
		
		ArrayList<Server> selectServerList = sService.selectServerList(loginMember);

		if(selectServerList != null || !selectServerList.isEmpty()) {
			model.addAttribute("selectServerList", selectServerList);
		}
		
		model.addAttribute("serverNo", serverNo).addAttribute("member", loginMember);

//		Channel channel = new Channel();
//		channel.setServerNo(no);
//		channel.se
		ArrayList<Channel> channel= cService.chattingSidebar(serverNo);
		System.out.println(channel.toString());
		model.addAttribute("channel", channel);
		
		// 채널 message 가져오자
//		Integer channelNum = (Integer)channelNo;
		ArrayList<ChatMessage> chatList = cService.selectChatList(channelNo);
		model.addAttribute("chatList", chatList);

//		ArrayList<Chat> chatChannel= cService.chattingSidebar(no);
//		model.addAttribute("chatChannel", chatChannel);

		return "chat/chatting";

	}
	
	@MessageMapping("/chat/{channelNo}/{separetor}")
	public void sendMessage(@DestinationVariable("channelNo") int channelNo, @DestinationVariable("separetor") String separetor, ChatMessage message) {
		// 특정  채팅방(roomId)에 메시지를 전송
		System.out.println("channelNo : " + channelNo);
		System.out.println("separetor : " + separetor);
		System.out.println("nickName : " + message.getSender());
		System.out.println("message : " + message.getMessage());
		message.setRoomId(channelNo);
		message.setSeparetor(separetor);
		// firebaseStore
		cService.insertChat(message);
		messagingTemplate.convertAndSend("/sub/chatroom/" + channelNo, message);
	}
	
	@MessageMapping("/joinVoice")
	@SendTo("")
	
	@PostMapping("selectSmallestChatNo")
	@ResponseBody
	public int selectSmallestChatNo(@RequestParam("serverNo") int serverNo) {
		System.out.println(serverNo);
		ArrayList<Integer> channelNo = sService.selectChannelNo(serverNo);
		System.out.println(channelNo);
		Collections.sort(channelNo);
		return channelNo.get(0);
	}
	
	@GetMapping("updateChannelUser")
	@ResponseBody
	public int updateChannelUser() {
		System.out.println("유저 입장.");
		return 1;
	}


	@GetMapping("/tiny")
	public String tiny(){
		return "/tiny";
	}




	@PostMapping("/tiny2")
	@ResponseBody
	public Map<String, String> sendMessage(@RequestBody Map<String, String> request) {
		String message = request.get("message"); // TinyMCE에서 보낸 메시지 받기
		System.out.println("받은 메시지: " + message);

		// 메시지를 DB에 저장하거나 WebSocket을 통해 전송 가능

		Map<String, String> response = new HashMap<>();
		response.put("message", "메시지가 전송되었습니다!");
		return response;
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
