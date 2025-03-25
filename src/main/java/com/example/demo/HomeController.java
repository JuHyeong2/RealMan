package com.example.demo;


import java.util.ArrayList;


import com.example.demo.chat.model.vo.Channel;
import com.example.demo.chat.model.vo.ChatMessage;
import com.example.demo.member.controller.FriendController;
import com.example.demo.member.model.service.MemberService;
import com.example.demo.member.model.vo.Friend;
import com.example.demo.member.model.vo.ProfileImage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.demo.chat.model.service.ChatService;
import com.example.demo.chat.model.vo.DM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
		ArrayList<Friend> friendList = mService.friendList(m.getMemberNo());
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
		.addAttribute("friendList", friendList);

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
		try {
			DM existingDM = cService.findDMByMembers(loginMember.getMemberNo(), otherMemberNo);

			if (existingDM == null) {
				DM newDM = cService.createDM(loginMember.getMemberNo(), otherMemberNo);
				result.put("dmNo", newDM.getDmNo());
			} else {
				result.put("dmNo", existingDM.getDmNo());
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
		Member m = (Member) session.getAttribute("loginMember");

		System.out.println("내가누구야 ;" + m);
//		ArrayList<Friend> friendList = mService.friendList(m.getMemberNo());
//		System.out.println(friendList);
		// 1. dm 했던거 가져오기 (상대방 닉네임(fireBase에서), 프사(oracle))
		// 2. 프사 = 닉네임으로 memberNo를 가져와서 프사 가져오면 될듯
		model.addAttribute("dmNo", dmNo)
				.addAttribute("loginMember", m);
//				.addAttribute("friendList", friendList);
//		System.out.println("칭기목록:" + friendList);

		return "/main/sendDM";

	}



	@MessageMapping("/dm/{dmNo}/D")
	public void sendMessage(DM message, @DestinationVariable("dmNo") int dmNo) {
		System.out.println("dmNo : " + dmNo);
		System.out.println(message.toString());
		cService.insertDM(message); // 서비스 계층을 통해 Firestore에 메시지 저장
		messagingTemplate.convertAndSend("/sub/chatroom/" + dmNo, message);
//		return message; // 클라이언트에 메시지 전송

	}





}
