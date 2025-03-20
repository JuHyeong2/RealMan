package com.example.demo.preferences.model.service;

import com.example.demo.preferences.model.mapper.PrefsMapper;
import com.example.demo.preferences.model.vo.Device;
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
}

