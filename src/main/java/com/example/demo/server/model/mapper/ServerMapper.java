package com.example.demo.server.model.mapper;

import com.example.demo.member.model.vo.Member;
import com.example.demo.server.model.vo.Server;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ServerMapper {
    ArrayList<Server> serverList(int memberNo);

    ArrayList<Server> selectServerList(Member m);

	ArrayList<Integer> selectChannelNo(int serverNo);
}
