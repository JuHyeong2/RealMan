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
        device.setMemberNo(loginUser.getMemberNo());
        device.setDeviceId(fingerPrint);
        int resultUdtAudio = pService.updateAudio(device);
        System.out.println("오디오 업데이트 : " + (resultUdtAudio==1?"성공":"실패"));
    }

    @GetMapping("/audio/getPrefs")
    @ResponseBody
    public Device getAudioPrefs(HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        String fingerprint = (String) session.getAttribute("fingerprint");
        if (loginMember == null) {
            return null;
        }
        // 사용자 ID를 기반으로 오디오 설정 정보 조회
        Device audioPrefs = pService.getAudioPrefs(loginMember.getMemberNo(), fingerprint);

        return audioPrefs;
    }



}
