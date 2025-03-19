package com.example.demo.preferences.model.mapper;

import com.example.demo.preferences.model.vo.Device;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PrefsMapper {
    Device checkDevice(Device device);
    int insertDevice(Device device);

    int updateAudio(Device device);
}
