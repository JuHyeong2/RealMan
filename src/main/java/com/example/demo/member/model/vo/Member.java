package com.example.demo.member.model.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Member {
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
	private int serverNo;

}
