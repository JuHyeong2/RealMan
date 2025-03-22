package com.example.demo.member.model.vo;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Friend {

    private int friendNo;
    private int memberNo;
    private int friendMemberNo;
    private String friendStatus;
    private String friendName;
    private String memberName;
}
