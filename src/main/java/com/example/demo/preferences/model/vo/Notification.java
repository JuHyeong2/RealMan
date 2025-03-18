package com.example.demo.preferences.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Notification {
    private int memberNo;
    private String notifyStatus;
    private String notifyScope;
    private String chatType;
    private String timeType;
}
