package com.example.demo.preferences.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class    Device {
    private int deviceNo;
    private int memberNo;
    private String deviceId;
    private String audioInput;
    private String audioOutput;
    private String video;
    private int inputValue;
    private int outputValue;
    private String inputType;
}
