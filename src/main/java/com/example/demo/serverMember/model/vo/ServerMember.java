package com.example.demo.serverMember.model.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString


public class ServerMember {
	private String isServerAdmin;
	private int memberNo;
	private String memberId;
	private String memberPwd;
	private String memberGender;
	private int memberPhone;
	private int memberBirth;
	private String memberEmail;
	private String memberNickname;
	private String memberStatus;
	private String memberIsAdmin;
	private Date memberCreateDate;
	private String profileImage;
	private String imageUrl;
}

