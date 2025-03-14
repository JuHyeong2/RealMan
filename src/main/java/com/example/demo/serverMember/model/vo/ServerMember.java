package com.example.demo.serverMember.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString


public class ServerMember {
    private int memberNo;
    private int serverNo;
    private String isAdmin;
    private String memberNickName;

}

