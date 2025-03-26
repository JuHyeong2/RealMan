package com.example.demo;


import java.util.ArrayList;


import com.example.demo.chat.model.vo.Channel;
import com.example.demo.chat.model.vo.ChatMessage;
import com.example.demo.member.controller.FriendController;
import com.example.demo.member.model.service.MemberService;
import com.example.demo.member.model.vo.Friend;
import com.example.demo.member.model.vo.ProfileImage;
import com.example.demo.preferences.model.vo.Notification;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.demo.chat.model.service.ChatService;
import com.example.demo.chat.model.vo.DM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.member.model.vo.Member;
import com.example.demo.server.model.service.ServerService;
import com.example.demo.server.model.vo.Server;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class HomeController {
	private final ServerService sService;
	private final ChatService cService;
	private final MemberService mService;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private final SimpMessagingTemplate messagingTemplate;



	@GetMapping("home")
	public String home() {
		return "index";
	}
	

	@GetMapping("/main")
	public String mainPage(Model model, HttpSession session, DM dmContent) {
		Member m = (Member) session.getAttribute("loginMember");
		ArrayList<Server> selectServerList = sService.selectServerList(m);

		ArrayList<Integer> friendNumberList = mService.selectFriendNumbers(m);
//		ArrayList<Friend> friendList = mService.friendList(m.getMemberNo());
		ArrayList<Friend> addFriendList = mService.addFriendList(m.getMemberNo());
		ArrayList<Friend> acceptFriendList = mService.acceptFriendList(m.getMemberNo());
		ArrayList<Friend> friendList = new ArrayList<>();
			friendList.addAll(addFriendList);
			friendList.addAll(acceptFriendList);



		ArrayList<DM> d = cService.selectDmList(m.getMemberNo());


		for(int i=0; i<d.size(); i++) {
			ProfileImage img = mService.selectImage(d.get(i).getMemberNo());
//			System.out.println(img);
			if(img != null) {
				d.get(i).setImageUrl(img.getImgRename());
			}
		}



		for(DM a : d){
			System.out.println("ㅋㅋㅋㅋㅋㅋㅋㅋㅋ" + a.toString());
		}

		model.addAttribute("loginMember", m)
		.addAttribute("DM",d)
		.addAttribute("friendNumberList", friendNumberList)
		.addAttribute("friendList", friendList)
		.addAttribute("addFriendList", addFriendList)
		.addAttribute("acceptFriendList", acceptFriendList);

		if(selectServerList != null || !selectServerList.isEmpty()) {
			model.addAttribute("selectServerList", selectServerList);
			
			
		}

//		cService.insertDM(dmContent);


		return "/main/main";
	}
	
	public int smallestTextChatNo() {
		
		return 0;
	}

//	@PostMapping("/dm/createDM")
//	@ResponseBody
//	public Map<String, Object> createDM(@RequestParam int otherMemberNo, HttpSession session) {
//		Member loginMember = (Member) session.getAttribute("loginMember");
//		Map<String, Object> result = new HashMap<>();
//
//
//		DM existingDM = cService.findDMByMembers(loginMember.getMemberNo(), otherMemberNo);
//
//		if (existingDM == null) {
//			// 새 DM 생성 후 Firestore에 저장
//			DM newDM = cService.createDM(loginMember.getMemberNo(), otherMemberNo);
//			result.put("dmNo", newDM.getDmNo());
//		} else {
//			// 기존 DM이 있으면 반환
//			result.put("dmNo", existingDM.getDmNo());
//		}
//
//		return result;
//
//	}
	@PostMapping("/dm/createDM")
	@ResponseBody
	public Map<String, Object> createDM(@RequestBody HashMap<String, Integer> map, HttpSession session) {
		Member loginMember = (Member) session.getAttribute("loginMember");
		Map<String, Object> result = new HashMap<>();
		int otherMemberNo = map.get("otherMemberNo");
		System.out.println(" 아더맴파" + otherMemberNo);
		map.put("memberNo", loginMember.getMemberNo());
		try {
			DM existingDM = cService.findDMByMembers(loginMember.getMemberNo(), otherMemberNo);

			if (existingDM == null) {
				int num = cService.createDM(map);
				System.out.println(map);
				if(num > 0) {
					result.put("dmNo", map.get("dmNo"));
				}else{
					throw new Exception();
				}
			}else{
				DM dm = cService.selectDmUseNickname(map);
				result.put("dmNo", dm.getDmNo());
			}
			return result;
		} catch (Exception e) {
			logger.error("채팅방 생성 중 오류 발생:", e);
			result.put("error", "채팅방 생성 실패");
			return result;
		}
	}





	@GetMapping("/dm/{dmNo}")
	public String dm(@PathVariable("dmNo") int dmNo, Model model, HttpSession session) {
		Member loginMember = (Member) session.getAttribute("loginMember");
		Notification notify = (Notification) session.getAttribute("notify");

//		System.out.println("내가누구야 ;" + loginMember);

		// 1. dm 했던거 가져오기 (상대방 닉네임(fireBase에서), 프사(oracle))
		// 2. 프사 = 닉네임으로 memberNo를 가져와서 프사 가져오면 될듯



		ArrayList<DM> DMList = cService.selectDm(dmNo, notify.getTimeType());
		for(int i=0; i<DMList.size(); i++) {
			Member m = mService.selectMemberNo(DMList.get(i).getSender());
			if(m != null) {
				ProfileImage img = mService.selectImage(m.getMemberNo());
				if(img != null) {
					DMList.get(i).setProfileUrl(img.getImgRename());
				}
			}
		}

		ArrayList<DM> d = cService.selectDmList(loginMember.getMemberNo());
		ArrayList<Integer> friendNumberList = mService.selectFriendNumbers(loginMember);
		ArrayList<Friend> friendList = mService.friendList(loginMember.getMemberNo());


		for(int i=0; i<d.size(); i++) {
			ProfileImage img = mService.selectImage(d.get(i).getMemberNo());
//			System.out.println(img);
			if(img != null) {
				d.get(i).setImageUrl(img.getImgRename());
			}
		}



		model.addAttribute("dmNo", dmNo)
				.addAttribute("loginMember", loginMember)
				.addAttribute("dmList", DMList)
				.addAttribute("DM",d)
				.addAttribute("friendNumberList", friendNumberList)
				.addAttribute("friendList", friendList);


		System.out.println("가벼운남자"+DMList);

		return "/main/sendDM";

	}



	@MessageMapping("/dm/{dmNo}/D")
	public void sendMessage(DM message, @DestinationVariable("dmNo") int dmNo, SimpMessageHeaderAccessor headerAccessor) {
//		System.out.println("dmNo : " + dmNo);

		HttpSession session = (HttpSession) headerAccessor.getSessionAttributes().get("HTTP_SESSION");
		System.out.println(session);
		Member m = (Member)session.getAttribute("loginMember");

		System.out.println("채널에들어와서 메시지 "+message.toString());
		message.setProfileUrl(m.getImageUrl());
//		message.setDmNo(m.getDmNo());
//		message.setOtherMemberNickname(m.getOtherMemberNickname());

		cService.insertDM(message); // 서비스 계층을 통해 Firestore에 메시지 저장
		messagingTemplate.convertAndSend("/sub/dm/" + dmNo, message);

	}





}
