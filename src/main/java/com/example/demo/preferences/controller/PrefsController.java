package com.example.demo.preferences.controller;

import com.example.demo.member.model.vo.Member;
import com.example.demo.preferences.model.service.PrefsService;
import com.example.demo.preferences.model.vo.Device;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/prefs")
public class PrefsController {

    private final PrefsService pService;

    @GetMapping({"", "/", "/ui-theme"})
    public String preferences() {
        return "ui-theme";
    }

    @GetMapping("/myProfile")
    public String myProfileView() {
        return "myProfile";
    }

    @GetMapping("/uiTheme")
    public String uiThemeView() {
        return "ui-theme";
    }

    @GetMapping("/notifications")
    public String notificationsView() {
        return "notifications";
    }

    @GetMapping("/messages")
    public String messagesView() {
        return "messages";
    }

    @GetMapping("/audio")
    public String audioView() {
        return "audio";
    }

    @GetMapping("/video")
    public String videoView() {
        return "video";
    }
    
    @PostMapping("/audio")
    @ResponseBody
    public void updateAudio(@RequestBody Device device, HttpSession session){
        Member loginUser = (Member) session.getAttribute("loginMember");
        String fingerPrint = (String) session.getAttribute("fingerprint");
        if(loginUser == null){
            return;
        }
        device.setMemberNo(loginUser.getMemberNo());   // 기존 null이었던 필드에 값 설정
        device.setDeviceId(fingerPrint);
        int resultUdtAudio = pService.updateAudio(device);
        System.out.println("오디오 업데이트 : " + (resultUdtAudio==1?"성공":"실패"));
    }

//    @PostMapping("/audio")
//    @ResponseBody
//    public ResponseEntity<Void> updateAudio(@RequestBody Device device, HttpSession session) {
//        System.out.println("✅ updateAudio() 실행됨");
//
//        // 세션 ID 출력 (세션이 유지되고 있는지 확인)
//        System.out.println("🔹 세션 ID: " + session.getId());
//
//        // 현재 세션에 저장된 모든 속성 확인
//        Enumeration<String> attributeNames = session.getAttributeNames();
//        while (attributeNames.hasMoreElements()) {
//            String attributeName = attributeNames.nextElement();
//            System.out.println("🔹 세션에 저장된 속성: " + attributeName + " = " + session.getAttribute(attributeName));
//        }
//
//        Member loginUser = (Member) session.getAttribute("loginUser");
//        if (loginUser == null) {
//            System.out.println("❌ 로그인 정보 없음 - 401 반환");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        System.out.println("🔹 로그인 유저 확인됨: " + loginUser.getMemberNo());
//
//        return ResponseEntity.noContent().build();
//    }



}
