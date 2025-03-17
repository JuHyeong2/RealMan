package com.example.demo.preferences.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/prefs")
public class PrefsController {

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
    
    /*
    // 카메라 장치 목록을 반환하는 API
    @ResponseBody
    @GetMapping("/video/devices")
    public List<Map<String, String>> getVideoDevices(HttpServletRequest request) {
        List<Map<String, String>> devices = new ArrayList<>();
        Map<String, String> device1 = new HashMap<>();
        device1.put("id", "device1");
        device1.put("label", "기본 카메라");

        Map<String, String> device2 = new HashMap<>();
        device2.put("id", "device2");
        device2.put("label", "외부 웹캠");

        devices.add(device1);
        devices.add(device2);

        return devices;
    }

    // 비디오 연결 테스트 API
    @ResponseBody
    @PostMapping("/video/test")
    public Map<String, String> testVideoConnection() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "비디오 연결이 성공적으로 테스트되었습니다.");
        return response;
    }
    */
    
    
//    @ResponseBody
//    @PostMapping("/video/test")
//    public Map<String, String> testVideoConnection() {
//        Map<String, String> response = new HashMap<>();
//        response.put("status", "success");
//        response.put("message", "비디오 연결이 성공적으로 테스트되었습니다.");
//        return response;
//    }
    // 혹시 몰라서 주석처리하고 안지움
    // 설정 에서는 장치 연결 테스트만 하고
    // 설정에서 선택한 카메라를 화상통화에서 사용하려면 장치설정 내용 세션에 저장하는 코드 필요
}
