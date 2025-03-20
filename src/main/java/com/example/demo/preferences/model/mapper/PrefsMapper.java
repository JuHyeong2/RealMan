package com.example.demo.preferences.model.mapper;

import com.example.demo.preferences.model.vo.Device;
import com.example.demo.preferences.model.vo.Notification;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PrefsMapper {
    Device checkDevice(Device device);
    int insertDevice(Device device);

    int updateAudio(Device device);

    int updateVideo(Device device);

    int updateNotify(Notification notify);

    Notification getNotifyPrefs(int memberNo);

    int insertNotify(int memberNo);

    int insertTheme(int memberNo);

    int updateMsg(Notification msg);
}
