package com.example.demo.preferences.model.mapper;

import com.example.demo.preferences.model.vo.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


@Mapper
public interface PrefsMapper {
    Device checkDevice(Device device);
    int insertDevice(Device device);

    int updateAudio(Device device);
}
