package com.example.demo.member.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
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

	// (아이디찾기, 비밀번호찾기)이메일 보내기
	@GetMapping("/sendEmail")
	@ResponseBody
	public String sendEmail(@RequestParam("email") String email) {
		System.out.println("sendEmail : " + email);
		String random = "";
		// 1. 가입된 이메일인지 확인
		int emailchecked = mService.checkEmail(email);
		
		System.out.println(emailchecked);
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
	public String resetPwd(@ModelAttribute Member m, @RequestParam("newPwd") String newPwd, Model model) {
		System.out.println("memberId : " + m.getMemberId());
		System.out.println("memberEmail : " + m.getMemberEmail());
		System.out.println("newPwd : " + newPwd);

		// 1. 사용자가 입력한 아이디와 이메일이 일치하는지 조회
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
	
	//회원 찾기
	@GetMapping("/find")
	@ResponseBody
	public ArrayList<Member> findMember(@RequestParam("search") String search,
			HttpSession session, Model model){
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("search", search);
		searchMap.put("myMemberNo", loginMember.getMemberNo()+"");
		ArrayList<Member> list = mService.findMember(searchMap);
		System.out.println("list : "+list);
		return list;
	}
	
	// 회원 차단
	@PostMapping("/block")
	@ResponseBody
	public int blockMember(@RequestBody HashMap<String, Integer> map2,
			HttpSession session) {
		int result = 0;
		int blockMemberNo = map2.get("bnm");
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("myMemberNo", loginMember.getMemberNo());
		map.put("blockMemberNo", blockMemberNo);
		
		//친구관계면 삭제
		HashMap<String, String> friendCheck = mService.friendCheck(map);
		if (friendCheck == null) {
			mService.deleteFriend(map);
		}
		
		//차단 여부 확인
		int blockCheck = mService.blockCheck(map);
		if (blockCheck == 0 ) {
			result = mService.blockMember(map);
		}
		
		return result;
	}
	
	@PutMapping("/edit")
	@ResponseBody
	public int editMemberInfo(@RequestBody HashMap<String, String> map,
			HttpSession session) {
		int result = 0;
		Member loginMember = (Member) session.getAttribute("loginMember");
		if(bcrypt.matches(
				bcrypt.encode(map.get("pwd")), loginMember.getMemberPwd())) {
			map.put("memberNo", loginMember.getMemberNo()+"");
			switch(map.get("col")) {
			case "member_nickname":
				if(!mService.isMemberNicknameDuplicated(map.get("val"))) {
					result = mService.editMemberInfo(map);
				}else {
					throw new MemberException("중복된 별명입니다.");
				}
				break;
			case "member_phone":
				if(!mService.isMemberPhoneDuplicated(map.get("val"))) {
					result = mService.editMemberInfo(map);
				}else {
					throw new MemberException("이미 등록된 번호입니다.");
				}
				break;
			case "member_email":
				if(!mService.isMemberEmailDuplicated(map.get("val"))) {
					result = mService.editMemberInfo(map);
				}else {
					throw new MemberException("이미 등록된 이메일입니다.");
				}
				break;
			}
		}else {
			throw new MemberException("비밀번호가 일치하지 않습니다.");
		}
		return result;
	}

	// 회원가입 페이지로 이동
	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	// 회원가입 처리
	@PostMapping("/signup")
	public String signup(@ModelAttribute Member m,
	                     @RequestParam("emailId") String emailId,
	                     @RequestParam("emailDomain") String emailDomain,
	                     @RequestParam(value = "customEmailDomain", required = false) String customEmailDomain,
	                     Model model) {

	    String fullEmail = emailId + "@" + ("custom".equals(emailDomain) ? customEmailDomain : emailDomain);
	    m.setMemberEmail(fullEmail);
	    
	    // 회원 상태 및 관리자 여부 기본값 설정
	    if (m.getMemberStatus() == null) {
	        m.setMemberStatus("Y");  
	    }
	    if (m.getMemberIsAdmin() == null) {
	        m.setMemberIsAdmin("N");  
	    }

	    // 중복 체크
	    if (mService.isMemberIdDuplicated(m.getMemberId())) {
	        model.addAttribute("errorMessage", "이미 사용 중인 아이디입니다.");
	        return "signup";
	    }

	    if (mService.isMemberNicknameDuplicated(m.getMemberNickname())) {
	        model.addAttribute("errorMessage", "이미 사용 중인 닉네임입니다.");
	        return "signup";
	    }

	    if (mService.isMemberPhoneDuplicated(String.valueOf(m.getMemberPhone()))) {
	        model.addAttribute("errorMessage", "이미 등록된 전화번호입니다.");
	        return "signup";
	    }

	    if (mService.isMemberEmailDuplicated(m.getMemberEmail())) {
	        model.addAttribute("errorMessage", "이미 사용 중인 이메일입니다.");
	        return "signup";
	    }

	    // 회원가입 진행
	    int result = mService.insertMember(m);
	    if (result > 0) {
	        return "redirect:/member/signin";
	    } else {
	        throw new MemberException("회원가입에 실패하였습니다.");
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
