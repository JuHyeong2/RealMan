package com.example.demo.chat.model.vo;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Channel {
	private int channelNo;
	private int serverNo;
	private String channelName;
	private Date channelCreatedate;
	private String channelSeparator;
	private String channelStatus;
}
