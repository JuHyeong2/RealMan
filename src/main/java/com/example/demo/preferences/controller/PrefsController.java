package com.example.demo.preferences.controller;

import com.example.demo.member.model.vo.Member;
import com.example.demo.preferences.model.service.PrefsService;
import com.example.demo.preferences.model.vo.Device;
import com.example.demo.preferences.model.vo.Notification;
import com.example.demo.preferences.model.vo.Theme;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.traversal.NodeIterator;

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

    @PostMapping("/video")
    @ResponseBody
    public void updateVideo(@RequestBody Device device, HttpSession session){
        Member loginUser = (Member) session.getAttribute("loginMember");
        String fingerPrint = (String) session.getAttribute("fingerprint");
        if(loginUser == null){
            return;
        }
        device.setMemberNo(loginUser.getMemberNo());
        device.setDeviceId(fingerPrint);
        int resultUdtVideo = pService.updateVideo(device);
        System.out.println("비디오 업데이트 : " + (resultUdtVideo==1?"성공":"실패"));
    }

    @GetMapping("/video/getPrefs")
    @ResponseBody
    public Device getVideoPrefs(HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        String fingerprint = (String) session.getAttribute("fingerprint");
        if (loginMember == null) {
            return null;
        }
        // 사용자 ID를 기반으로 오디오 설정 정보 조회
        Device videoPrefs = pService.getVideoPrefs(loginMember.getMemberNo(), fingerprint);
        return videoPrefs;
    }

    @PostMapping("/notifications")
    @ResponseBody
    public void updateNotify(@RequestBody Notification notify, HttpSession session){
        Member loginUser = (Member) session.getAttribute("loginMember");
        notify.setMemberNo(loginUser.getMemberNo());
        int resultUdtNotify = pService.updateNotify(notify);
        System.out.println("알림 업데이트 : " + (resultUdtNotify ==1?"성공":"실패"));
    }

    @GetMapping("/notifications/getPrefs")
    @ResponseBody
    public Notification getNotifyPrefs(HttpSession session){
        Member loginMember = (Member) session.getAttribute("loginMember");
        Notification notify = pService.getNotifyPrefs(loginMember.getMemberNo());
        return notify;
    }
    s
    @PostMapping("/messages")
    @ResponseBody
    public void updateMessage(@RequestBody Notification msg, HttpSession session){
        Member loginUser = (Member) session.getAttribute("loginMember");
        msg.setMemberNo(loginUser.getMemberNo());
        int resultUdtMsg = pService.updateMsg(msg);
        Notification notify = (Notification) session.getAttribute("notify");
        notify.setTimeType(msg.getTimeType());
        notify.setChatType(msg.getChatType());
        session.setAttribute("notify", notify);
        System.out.println("메세지타입 업데이트 : " + (resultUdtMsg ==1?"성공":"실패"));
    }

    @GetMapping("/messages/getPrefs")
    @ResponseBody
    public Notification getMsgPrefs(HttpSession session){
        Member loginMember = (Member) session.getAttribute("loginMember");
        Notification msg = pService.getNotifyPrefs(loginMember.getMemberNo());
        session.setAttribute("chatType", msg.getChatType());
        session.setAttribute("timeType", msg.getTimeType());
        return msg;
    }

    @PostMapping("/ui-theme")
    @ResponseBody
    public void updateTheme(@RequestBody Theme theme, HttpSession session){
        Member loginUser = (Member) session.getAttribute("loginMember");
        theme.setMemberNo(loginUser.getMemberNo());
        int resultUdtTheme = pService.updateTheme(theme);
        System.out.println("테마 업데이트 : " + (resultUdtTheme ==1?"성공":"실패"));
        if(resultUdtTheme == 1){
            session.setAttribute("theme", theme);
        }
    }

    @GetMapping("/ui-theme/getPrefs")
    @ResponseBody
    public Theme getThemePrefs(HttpSession session){
        Member loginMember = (Member) session.getAttribute("loginMember");
        Theme getTheme = pService.getThemePrefs(loginMember.getMemberNo());

        return getTheme;
    }

}
