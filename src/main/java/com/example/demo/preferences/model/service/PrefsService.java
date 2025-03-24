package com.example.demo.preferences.model.service;

import com.example.demo.preferences.model.mapper.PrefsMapper;
import com.example.demo.preferences.model.vo.Device;
import com.example.demo.preferences.model.vo.Notification;
import com.example.demo.preferences.model.vo.Theme;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrefsService {
    private final PrefsMapper prefsMapper;
    public void saveDevice(int memberNo, String fingerprint) {

        Device device = new Device();
        device.setMemberNo(memberNo);
        device.setDeviceId(fingerprint);
        System.out.println("saveDevice 호출됨, memberNo: " + memberNo + ", fingerprint: " + fingerprint);

        // 동일한 fingerprint가 이미 존재하는지 확인
        Device checkDevice = prefsMapper.checkDevice(device);
        if (checkDevice == null) {
            // fingerprint가 다르면 새로운 디바이스 추가
           int result = prefsMapper.insertDevice(device);
           System.out.println("fingerprint 들어갔나? : "+result);
        }
        // 동일한 fingerprint가 있으면 아무 동작도 하지 않음 (중복 저장 방지)
    }

    public int updateAudio(Device device) {
        return prefsMapper.updateAudio(device);
    }

    public Device getAudioPrefs(int memberNo, String fingerprint) {
        Device device = new Device();
        device.setMemberNo(memberNo);
        device.setDeviceId(fingerprint);
        return prefsMapper.checkDevice(device);
    }

    public int updateVideo(Device device) {
        return prefsMapper.updateVideo(device);
    }

    public Device getVideoPrefs(int memberNo, String fingerprint) {
        Device device = new Device();
        device.setMemberNo(memberNo);
        device.setDeviceId(fingerprint);
        return prefsMapper.checkDevice(device);
    }

    public int updateNotify(Notification notify) {
        return prefsMapper.updateNotify(notify);
    }

    public Notification getNotifyPrefs(int memberNo) {
        return prefsMapper.getNotifyPrefs(memberNo);
    }


    public void inesrtDefaultSetting(int memberNo) {
        int resultNotify = prefsMapper.insertNotify(memberNo);
        int resultTheme = prefsMapper.insertTheme(memberNo);
        System.out.println(resultTheme+resultNotify==2?"설정기본값생성완료":"설정기본값생성실패");
    }

    public int updateMsg(Notification msg) {
        return prefsMapper.updateMsg(msg);
    }

    public int updateTheme(Theme theme) {
        return prefsMapper.updateTheme(theme);
    }

    public Theme getThemePrefs(int memberNo) {
        return prefsMapper.getThemePrefs(memberNo);
    }
}

