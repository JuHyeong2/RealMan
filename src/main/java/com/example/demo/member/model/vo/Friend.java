package com.example.demo.member.model.vo;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Friend {

    private int friendNo;
    private String friendNickname;
    private int friendMemberNo;
    private String friendStatus;

    private int memberNo;
    private String memberNickname;
}
