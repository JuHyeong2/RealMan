package com.example.demo.server.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.member.model.vo.Member;
import com.example.demo.server.model.vo.Server;
import com.example.demo.serverMember.model.vo.ServerMember;

@Mapper
public interface ServerMapper {
    ArrayList<Server> serverList(int memberNo);

    ArrayList<Server> selectServerList(Member m);

	ArrayList<Integer> selectChannelNo(int serverNo);
	
	int inviteMember(HashMap<String, Integer> map);

}
