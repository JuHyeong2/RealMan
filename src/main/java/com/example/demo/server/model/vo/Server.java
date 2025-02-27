package com.example.demo.server.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Server {
    private int serverNo;
    private String serverName;
    private Date serverCreatedate;
    private String serverStatus;
}
