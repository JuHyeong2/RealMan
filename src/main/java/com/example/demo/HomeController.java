package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("home")
	public String home() {
		return "index";
	}
	
	 @GetMapping("/main/main")
	    public String mainPage() {
		 System.out.println("✅ main.html 페이지 요청됨!");
	        return "main/main"; 
	    }
	
}
