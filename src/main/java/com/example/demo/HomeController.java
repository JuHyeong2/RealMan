package com.example.demo;

import com.example.demo.server.model.service.ServerService;
import com.example.demo.server.model.vo.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class HomeController {
	private final ServerService sService;


	@GetMapping("home")
	public String home() {
		return "index";
	}
	
	@GetMapping("/main")
	public String mainPage(Model model) {
//  System.out.println("✅ main.html 페이지 요청됨!");

	Server server = new Server();
	ArrayList<Server> selectServerList = sService.selectServerList();

	model.addAttribute("server", server);
	model.addAttribute("selectServerList",selectServerList);

	return "/main/main";
	}
	
}
