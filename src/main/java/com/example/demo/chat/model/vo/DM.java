package com.example.demo.chat.model.vo;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
