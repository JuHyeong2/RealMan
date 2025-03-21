package com.example.demo.chat.controller;


import java.io.Console;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.example.demo.chat.model.vo.DM;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.demo.HomeController;
import com.example.demo.chat.model.service.ChatService;
import com.example.demo.chat.model.vo.Channel;
import com.example.demo.chat.model.vo.ChannelMember;
import com.example.demo.chat.model.vo.ChatMessage;
import com.example.demo.member.model.service.MemberService;
import com.example.demo.member.model.vo.Member;
import com.example.demo.member.model.vo.ProfileImage;
import com.example.demo.server.model.service.ServerService;
import com.example.demo.server.model.vo.Server;
import com.example.demo.serverMember.model.service.ServerMemberService;
import com.example.demo.serverMember.model.vo.ServerMember;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final HomeController homeController;
	private final ChatService cService;
	private final ServerService sService;
	private final ServerMemberService smService;
	private final MemberService mService;
	

	private final SimpMessagingTemplate messagingTemplate;
	
	private Map<Integer, Set<String>> userInChannel = new ConcurrentHashMap<>();
	private Map<Integer, Set<String>> videoInChannel = new ConcurrentHashMap<>();
	private int memberInchannelNo = 0;

	@GetMapping("/main")
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
		
		ArrayList<Server> selectServerList = sService.selectServerList(loginMember);
		if(selectServerList != null || !selectServerList.isEmpty()) {
			model.addAttribute("selectServerList", selectServerList);
		}

		model.addAttribute("serverNo", serverNo).addAttribute("member", loginMember);

//		Channel channel = new Channel();
//		channel.setServerNo(no);
//		channel.se
		ArrayList<Channel> channel= cService.chattingSidebar(serverNo);

//		System.out.println(channel.toString());
		model.addAttribute("channel", channel);

		ArrayList<ServerMember> memberList = smService.serverMemberList(serverNo);
		System.out.println("memberList : "+memberList);
		model.addAttribute("memberList", memberList);
		
		//로그인멤버의 서버관리자 여부
		for(ServerMember member : memberList) {
			if(loginMember.getMemberNo() == member.getMemberNo()) {
				if(member.getIsServerAdmin().equals("Y")) {
					model.addAttribute("admin", true);
				}else {
					model.addAttribute("admin", false);
				}
			}
		}

		// 채널 message 가져오자
//		Integer channelNum = (Integer)channelNo;
		ArrayList<ChatMessage> chatList = cService.selectChatList(channelNo);
		for(int i=0; i<chatList.size(); i++) {
			Member m = mService.selectMemberNo(chatList.get(i).getSender());
			if(m != null) {
				ProfileImage img = mService.selectImage(m.getMemberNo());
				if(img != null) {
					chatList.get(i).setProfileUrl(img.getImgRename());
				}
			} 
		}
		model.addAttribute("chatList", chatList);

		//서버멤버 가져오기
		for(int i=0; i<memberList.size(); i++) {
			ProfileImage img = mService.selectImage(memberList.get(i).getMemberNo());
//			System.out.println(img);
			if(img != null) {
				memberList.get(i).setImageUrl(img.getImgRename());
				System.out.println(memberList.get(i).getMemberNickname()+" : "+memberList.get(i).getImageUrl());
			}
		}
		model.addAttribute("memberList", memberList);

		
		
		// 채널이 Voice인지 Chat인지 확인
		Channel Channel = cService.selectChannel(channelNo);
		System.out.println("V or C ? : " + Channel.getChannelSeparator());
		
		if(Channel.getChannelSeparator() != null && Channel.getChannelSeparator() != "") {
			if(Channel.getChannelSeparator().equals("V")) {
				return "chat/videoChatting";
			} else {
				return "chat/chatting";
			}
		}
		



//		ArrayList<Chat> chatChannel= cService.chattingSidebar(no);
//		model.addAttribute("chatChannel", chatChannel);

		return "";

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
	
	// 입장시 추가 후 목록반환
	@MessageMapping("/chat/joinVoice")
	@SendTo("/sub/voice")
	public Map<Integer, Set<String>> joinVoiceChannel (@Payload ChannelMember cMember){
		System.out.println("joinVoice 들어옴.");
		System.out.println(cMember.toString());
		memberInchannelNo = cMember.getClickServerNo();
		for(int key : userInChannel.keySet()) {
			if(userInChannel.get(key).contains(cMember.getUsername())) {
				userInChannel.get(key).remove(cMember.getUsername());
			}
		}
		userInChannel.computeIfAbsent(cMember.getClickServerNo(), k -> ConcurrentHashMap.newKeySet()).add(cMember.getUsername());
		return userInChannel;
	}
	
	// 목록반환 만
	@GetMapping("/api/voiceUsers")
	@ResponseBody
	public Map<Integer, Set<String>> joinVoiceChannel () {
		System.out.println("/api/voiceUsers 들어옴.");
		System.out.println(userInChannel);
	//messagingTemplate.convertAndSend("/sub/voice", userInChannel);
		return userInChannel;
	}
	
	// 사용자 목록 업데이트 후 반환
	@MessageMapping("/chat/leaveVoice")
	@SendTo("/sub/voice")
	public Map<Integer, Set<String>> leaveVoiceChannel (@Payload String username){
		System.out.println("보이스채팅을 나가는 닉네임 : " + username);
		for (int key : userInChannel.keySet()) {
			if (userInChannel.get(key).contains(username)) {
				userInChannel.get(key).remove(username);
			}
		}
		return userInChannel;
	}
	
	
	@PostMapping("selectSmallestChatNo")
	@ResponseBody
	public int selectSmallestChatNo ( @RequestParam("serverNo") int serverNo){
		System.out.println(serverNo);
		ArrayList<Integer> channelNo = sService.selectChannelNo(serverNo);
		System.out.println(channelNo);
		Collections.sort(channelNo);
		return channelNo.get(0);
	}
	
	@GetMapping("updateChannelUser")
	@ResponseBody
	public int updateChannelUser () {
	//System.out.println("유저 입장.");
		return 1;
	}
	
	//offer 정보를 주고 받기 위한 websocket
	//camKey : 각 요청하는 캠의 key , roomId : 룸 아이디
	@MessageMapping("/peer/offer/{camKey}/{roomId}")
	@SendTo("/sub/peer/offer/{camKey}/{roomId}")
	public String PeerHandleOffer (@Payload String offer, @DestinationVariable(value = "roomId") String roomId,
			@DestinationVariable(value = "camKey") String camKey){
		System.out.println("roomId : " + roomId);
		System.out.println("offer : " + offer);
		System.out.println("camKey : " + camKey);
	//	System.out.printf("[OFFER] {} : {}", camKey, offer);
	
		return offer;
	}
	
	//iceCandidate 정보를 주고 받기 위한 webSocket
	//camKey : 각 요청하는 캠의 key , roomId : 룸 아이디
	@MessageMapping("/peer/iceCandidate/{camKey}/{roomId}")
	@SendTo("/sub/peer/iceCandidate/{camKey}/{roomId}")
	public String PeerHandleIceCandidate(@Payload String candidate, @DestinationVariable(value = "roomId") String roomId,
	                                     @DestinationVariable(value = "camKey") String camKey) {
		System.out.println("iceCandidateroomId : " + roomId);
		System.out.println("iceCandidatecamKey : " + camKey);
		for(int key : videoInChannel.keySet()) {
			if(videoInChannel.get(key).contains(camKey)) {
				videoInChannel.get(key).remove(camKey);
			}
		}
		
		videoInChannel.computeIfAbsent(memberInchannelNo,  k -> ConcurrentHashMap.newKeySet()).add(camKey);

//		System.out.println("[ICECANDIDATE] {} : {}", camKey, candidate);
			return candidate;
		}

	@MessageMapping("/peer/answer/{camKey}/{roomId}")
	@SendTo("/sub/peer/answer/{camKey}/{roomId}")
	public String PeerHandleAnswer(@Payload String answer, @DestinationVariable(value = "roomId") String roomId,
	                               @DestinationVariable(value = "camKey") String camKey) {
		System.out.println("answerroomId : " + roomId);
		System.out.println("answercamKey : " + camKey);
		System.out.println("answer : " + answer);
//	    log.info("[ANSWER] {} : {}", camKey, answer);
	    return answer;
	}
	
	//camKey 를 받기위해 신호를 보내는 webSocket
	@MessageMapping("/call/key")
	@SendTo("/sub/call/key")
	public String callKey(@Payload String message) {
		System.out.println(message);
//	    log.info("[Key] : {}", message);
				return message;
	}

			//자신의 camKey 를 모든 연결된 세션에 보내는 webSocket
	@MessageMapping("/send/key")
	@SendTo("/sub/send/key")
	public String sendKey (@Payload String message){
		System.out.println("sendmessage : " + message);
		return message;
	}




	@GetMapping("/tiny")
	public String tiny () {
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




}
