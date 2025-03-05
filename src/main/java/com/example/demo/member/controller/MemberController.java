package com.example.demo.member.controller;

import java.util.ArrayList;

import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import com.example.demo.common.util.EmailCertificationUtil;
import com.example.demo.member.model.exception.MemberException;
import com.example.demo.member.model.service.MemberService;
import com.example.demo.member.model.vo.Member;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@SessionAttributes("loginMember")
public class MemberController {
	private final MemberService mService;
	private final BCryptPasswordEncoder bcrypt;
	private final EmailCertificationUtil emailUtil;

	// 아이디찾기 페이지로
	@GetMapping("/findMyId")
	public String findMyId() {
		return "/findMyId";
	}

	// 비번찾기 페이지로
	@GetMapping("/findMyPwd")
	public String findMyPwd() {
		return "/findMyPwd";
	}
	
	//친구목록 페이지로
//	@GetMapping("/friends")
//	public String friends(Model model) {
//		Member loginMember = (Member) model.getAttribute("loginMember");
//		ArrayList<Integer> friendNumberList = mService.selectFriendNumbers(loginMember);
//		ArrayList<Member> list = mService.selectFriends(friendNumberList);
//		model.addAttribute("list", list);
//		return "/friends";
//	}
	@GetMapping("/friends")
	public String friends() {
		return "/friends";
	}

	// (아이디찾기, 비밀번호찾기)이메일 보내기
	@GetMapping("/sendEmail")
	@ResponseBody
	public String sendEmail(@RequestParam("email") String email) {
		System.out.println("sendEmail : " + email);
		String random = "";
		// 1. 가입된 이메일인지 확인
		int emailchecked = mService.checkEmail(email);

		if (emailchecked == 1) {
			// 2. 코드 생성
			for (int i = 0; i < 6; i++) {
				String pool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
				int r = (int) (Math.random() * (pool.length() + 1));
				char c = pool.charAt(r);
				random += c;
			}
			// 3. 이메일 전송
			try {
				emailUtil.sendEmail(email, random);
				return random;
			} catch (MailException e) {
				return "MailException";
			} catch (MessagingException e) {
				return "MessagingException";
			}
		} else {
			return "EmailNotFound";
		}
	}

	// 아이디 보여주기
	@GetMapping("/findId")
	@ResponseBody
	public String findId(@RequestParam("email") String email) {
		String memberId = mService.findId(email);
		return memberId;
	}

	// 비밀번호 재설정하기
	@PostMapping("/resetPwd")
	@ResponseBody
	public String resetPwd(@ModelAttribute Member m,
			@RequestParam("newPwd") String newPwd,
			Model model) {
		System.out.println("memberId : " + m.getMemberId());
		System.out.println("memberEmail : " + m.getMemberEmail());
		System.out.println("newPwd : " + newPwd);

		// 1. 사용자가 입력한 아이디와 - 이메일이 일치하는지 조회
		int check = mService.confirmIdEmail(m);
		System.out.println("confirmIdEmail : " + check);

		if (check == 1) {
			m.setMemberPwd(bcrypt.encode(newPwd));

			int result = mService.resetPwd(m);
			System.out.println("resetPwd : " + result);

			if (result == 1) {
				// model.addAttribute("loginMember", m);
				return "success";
			}
		} else {
			return "MemberNotFound";
		}
		return "?";
	}

	// 회원가입 페이지로 이동
	@GetMapping("/signup")
    public String signup() {
        return "signup";
    }
	
	//회원가입 처리
	@PostMapping("/signup")
	public String signup(@ModelAttribute Member m,
	                     @RequestParam("emailId") String emailId,
	                     @RequestParam("emailDomain") String emailDomain,
	                     @RequestParam(value = "customEmailDomain", required = false) String customEmailDomain) {

	    if ("custom".equals(emailDomain) && customEmailDomain != null && !customEmailDomain.trim().isEmpty()) {
	        m.setMemberEmail(emailId + "@" + customEmailDomain);
	    } else {
	        m.setMemberEmail(emailId + "@" + emailDomain);
	    }

	    if (m.getMemberStatus() == null) {
	        m.setMemberStatus("Y"); // 기본값 'Y' 설정
	    }
	    if (m.getMemberIsAdmin() == null) {
	        m.setMemberIsAdmin("N"); // 기본값 'N' 설정
	    }

	    int result = mService.insertMember(m);
	    if (result > 0) {
	        return "redirect:/member/signin"; // 회원가입 성공 후 로그인 페이지로 이동
	    } else {
	        throw new MemberException("회원가입을 실패하였습니다.");
	    }
	}

	// 로그인 페이지로 이동
	 @GetMapping("/signin")
	 public String signIn() {
		 return "member/signin";
	 }

	// 로그인 처리
	 @PostMapping("/signin")
	 public String login(@RequestParam("memberId") String memberId,
	                     @RequestParam("memberPwd") String memberPwd,
	                     Model model, HttpSession session) {
	     Member loginMember = mService.login(memberId, memberPwd);

	     if (loginMember != null) {
	         model.addAttribute("loginMember", loginMember); 
	         session.setAttribute("loginMember", loginMember); 
	         return "redirect:/main";
	     } else {
	         model.addAttribute("errorMessage", "아이디 또는 비밀번호가 잘못되었습니다.");
	         return "member/signin";
	     }
	 }
	 
	 //로그 아웃 처리
	 @GetMapping("/logout")
	 public String logout(HttpSession session, SessionStatus status) {

	     session.removeAttribute("loginMember");
	     status.setComplete();
	     return "redirect:/";
	 }

}
