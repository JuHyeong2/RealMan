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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.ArrayList;

import com.example.demo.member.model.vo.Member;
import com.example.demo.server.model.service.ServerService;
import com.example.demo.server.model.vo.Server;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequiredArgsConstructor
public class HomeController {
	private final ServerService sService;
	private final ChatService cService;
	private final MemberService mService;



	@GetMapping("home")
	public String home() {
		return "index";
	}
	

	@GetMapping("/main")
	public String mainPage(Model model, HttpSession session, DM dmContent) {
		Member m = (Member) session.getAttribute("loginMember");
		ArrayList<Server> selectServerList = sService.selectServerList(m);

		ArrayList<Integer> friendNumberList = mService.selectFriendNumbers(m);
		ArrayList<DM> d = cService.selectDmList(m.getMemberNo());

		for(DM a : d){
			System.out.println("ㅋㅋㅋㅋㅋㅋㅋㅋㅋ" + a.toString());
		}

		model.addAttribute("loginMember", m)
				.addAttribute("DM",d)
		.addAttribute("friendNumberList", friendNumberList);

		if(selectServerList != null || !selectServerList.isEmpty()) {
			model.addAttribute("selectServerList", selectServerList);
			
			
		}

		cService.insertDM(dmContent);


		return "/main/main";
	}
	
	public int smallestTextChatNo() {
		
		return 0;
	}

	@GetMapping("/dm/{dmNo}")
	public String dm(@PathVariable int dmNo, Model model, HttpSession session) {
		Member m = (Member) session.getAttribute("loginMember");

		System.out.println(m);
		ArrayList<Friend> friendList = mService.friendList(m.getMemberNo());
		System.out.println(friendList);
		model.addAttribute("dmNo", dmNo)
				.addAttribute("loginMember", m)
				.addAttribute("friendList", friendList);

		return "/main/sendDM";

	}

	
}
