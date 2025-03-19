package com.example.demo.member.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileImage {
	private int imgNo;
	private String imgOriginalname;
	private String imgPath;
	private String imgRename;
	private String imgSeparator;
	private int mcdNo;
	private String url;
}
