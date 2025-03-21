package com.example.demo.chat.model.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
	private int roomId;
	private String sender;
	private String message;
	private String separetor;
	private String createDate;
	private String profileUrl;
}
