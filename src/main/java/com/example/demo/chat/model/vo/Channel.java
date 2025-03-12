package com.example.demo.chat.model.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Channel {
	private int channelNo;
	private int serverNo;
	private String channelName;
	private Date channelCreatedate;
	private String channelSeparator;
	private String channelStatus;
}
