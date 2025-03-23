package com.example.demo.server.controller;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.demo.member.model.vo.ProfileImage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.member.model.service.MemberService;
import com.example.demo.member.model.vo.Member;
import com.example.demo.server.model.exception.ServerException;
import com.example.demo.server.model.service.ServerService;
import com.example.demo.server.model.vo.Server;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/server")
public class ServerController {
    private final ServerService sService;
    private final MemberService mService;
	private final AmazonS3Client amazonS3;

	// aws S3 버켓 이름
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

    @GetMapping("/server")
    public String server(Member m ,@PathVariable("no") int no,
                         Model model, HttpSession session){
        Member loginMember = (Member) session.getAttribute("loginMember");
        m.setMemberNo(loginMember.getMemberNo());
        ArrayList<Server> serverList = new ArrayList<Server>(sService.serverList(loginMember.getMemberNo()));
        model.addAttribute("no", no);
        model.addAttribute("serverList", serverList);

        return "/common/sidebar";
    }

	@GetMapping("/list")
	@ResponseBody
	public List<Map<String, Object>> getServerList(HttpSession session) {
		Member loginMember = (Member) session.getAttribute("loginMember");
		List<Map<String, Object>> loadSL = sService.loadServerList(loginMember.getMemberNo());
		System.out.println("서버리스트" + loadSL);
		return  loadSL;
	}



	@GetMapping("/inviteList")
    @ResponseBody
    public ArrayList<Member> inviteList(HttpSession session){
    	Member loginMember = (Member) session.getAttribute("loginMember");
		ArrayList<Member> inviteList = sService.selectInviteList(loginMember.getMemberNo());
    	return null;
    }
   //회원 초대
    @PostMapping("/serverMember")
    @ResponseBody
    public int inviteMember(@RequestBody HashMap<String, Integer> map, 
    		HttpSession session) {
    	Member loginMember = (Member) session.getAttribute("loginMember");
    	int result = 0;

    	//서버 is_admin 확인
    	HashMap<String, Integer> map2 = new HashMap<String, Integer>();
    	map2.put("memberNo", loginMember.getMemberNo());
    	map2.put("serverNo", map.get("serverNo"));
    	String isAdmin = sService.checkIsAdmin(map2);
    	
    	if(isAdmin.equals("Y")) {
    		result = sService.inviteMember(map);
    	}else {
    		throw new RuntimeException("not admin");
    	}
    	
    	return result;
    }
    
    //회원 강퇴
    @DeleteMapping("/serverMember")
    @ResponseBody
    public int ejectMember(@RequestBody HashMap<String, Integer> map, 
    		HttpSession session) {
    	Member loginMember = (Member) session.getAttribute("loginMember");
    	int result = 0;
    	
    	//서버 is_admin 확인
    	HashMap<String, Integer> map2 = new HashMap<String, Integer>();
    	map2.put("memberNo", loginMember.getMemberNo());
    	map2.put("serverNo", map.get("serverNo"));
    	String isAdmin = sService.checkIsAdmin(map2);
    	
    	if(isAdmin.equals("Y")) {
    		result = sService.ejectMember(map);
    	}else {
    		throw new RuntimeException("not admin");
    	}
    	
    	return result;
    }
	@PostMapping("/add")
	public String addServer(@RequestParam("serverName") String name,
							@RequestParam(value = "image", required = false) MultipartFile image,
							HttpSession session, Model model) {

		Member loginMember = (Member) session.getAttribute("loginMember");

		// 1. 서버 생성
		int serverNo = sService.createServer(name, loginMember.getMemberNo());

		// 2. 이미지 업로드 (선택)
//		if (image != null && !image.isEmpty()) {
//			try {
//				String[] upload = saveFiles(image); // ← 이 메서드를 그대로 사용
//				ProfileImage profileImage = new ProfileImage();
//				profileImage.setImgName(image.getOriginalFilename());
//				profileImage.setImgPath(upload[1]);      // 리네임 파일명 (DB 저장용)
//				profileImage.setImgRename(upload[0]);    // 전체 URL (화면 표시용)
//				profileImage.setImgSeparator("C");       // 서버 아이콘
//				profileImage.setMcdNo(serverNo);         // server_no
//
//				mService.saveOrUpdateProfileImage(profileImage);
//			} catch (Exception e) {
//				e.printStackTrace();
//				model.addAttribute("errorMessage", "이미지 업로드 실패");
//				return "error";
//			}
//		}
		if(image != null && !image.isEmpty()) {
			String fileName = image.getOriginalFilename();

			String[] files = saveFiles(image);
			if (files[1] != null) {
				ProfileImage profileImage = new ProfileImage();
				profileImage.setImgName(fileName);
				profileImage.setImgPath(files[1]);
				profileImage.setImgRename(files[0]);
				profileImage.setImgSeparator("C");
				profileImage.setMcdNo(serverNo);
				mService.saveOrUpdateProfileImage(profileImage);
			}
		}

		// 3. 생성된 서버의 첫 채널로 이동
		return "redirect:/chat/main/" + serverNo + "/" + 1;
		// return "redirect:/chat/main/" + serverNo;
	}

	//파일 저장하기 메소드
	public String[] saveFiles(MultipartFile upload) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		int ranNum = (int)(Math.random()*100000);
		String originFileName = upload.getOriginalFilename();
		String renameFileName = sdf.format(new Date()) + ranNum + originFileName.substring(originFileName.lastIndexOf("."));

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(upload.getSize());
		metadata.setContentType(upload.getContentType());

		try {
			amazonS3.putObject(bucket, renameFileName, upload.getInputStream(), metadata);
		} catch (SdkClientException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] returnArr = new String[2];
		returnArr[0] = amazonS3.getUrl(bucket, renameFileName).toString();
		returnArr[1] = renameFileName;

		return returnArr;
	}

}
