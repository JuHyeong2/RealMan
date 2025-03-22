package com.example.demo.chat.model.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatMessage {
	private int roomId;
	private String sender;
	private String message;
	private String separetor;
	private String createDate;
	private String profileUrl;

}
