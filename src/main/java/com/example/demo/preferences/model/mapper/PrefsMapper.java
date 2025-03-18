package com.example.demo.preferences.model.mapper;

import com.example.demo.preferences.model.vo.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface PrefsMapper {
    Device checkDevice(int memberNo, String fingerprint);
    int insertDevice(int memberNo, String fingerprint);
}
