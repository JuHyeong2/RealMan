package com.example.demo.server.model.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.chat.model.vo.Channel;
import com.example.demo.member.model.vo.Member;
import com.example.demo.server.model.vo.Server;
import com.example.demo.serverMember.model.vo.ServerMember;

@Mapper
public interface ServerMapper {
    ArrayList<Server> serverList(int memberNo);

    ArrayList<Server> selectServerList(Member m);

	ArrayList<Map<String, Object>> loadServerList(int memberNo);

	ArrayList<Integer> selectChannelNo(int serverNo);

	ArrayList<Integer> selectMemberNumbers(int serverNo);

	String checkIsAdmin(HashMap<String, Integer> map2);

	int inviteMember(HashMap<String, Integer> map);

	ArrayList<Member> selectInviteList(int memberNo);

	int ejectMember(HashMap<String, Integer> map);

	void insertServer(String name);

	int selectCreateServerNo();

	void insertServerMember(HashMap<String, Integer> map);

	int insertDefaultTextChannel(Channel channel);

	int insertDefaultVoiceChannel(Channel channel);

	int insertChannel(Channel ch);

	int editChannel(HashMap<String, String> map);

	int deleteChannel(int channelNo);
}
