package com.example.demo.chat.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class DM {
    private int dmNo;
    private Date dmCreateDate;
    private char dmStatus;
    private int memberNo;
    private int otherMemberNo;

    private String memberNickname;
    private String otherMemberNickname;
    private String message;





}
