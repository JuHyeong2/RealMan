package com.example.demo.server.model.service;


import com.example.demo.chat.model.vo.Channel;
import com.example.demo.member.model.vo.Member;
import com.example.demo.server.model.mapper.ServerMapper;
import com.example.demo.server.model.vo.Server;
import com.example.demo.serverMember.model.vo.ServerMember;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ServerService {
    private final ServerMapper mapper;

    public ArrayList<Server> serverList(int memberNo) {
        return mapper.serverList(memberNo);
    }

    public ArrayList<Server> selectServerList(Member m) {
        return mapper.selectServerList(m);
    }
	public ArrayList<Map<String, Object>> loadServerList(int memberNo) {
		return mapper.loadServerList(memberNo);
	}

	public ArrayList<Integer> selectChannelNo(int serverNo) {
		
		return mapper.selectChannelNo(serverNo);
	}

	public ArrayList<Integer> selectMemberNumbers(int serverNo) {
		return mapper.selectMemberNumbers(serverNo);
	}

	public String checkIsAdmin(HashMap<String, Integer> map2) {
		return mapper.checkIsAdmin(map2);
	}

	public int inviteMember(HashMap<String, Integer> map) {
		return mapper.inviteMember(map);
	}

	public ArrayList<Member> selectInviteList(int memberNo) {
		return mapper.selectInviteList(memberNo);
	}

	public int ejectMember(HashMap<String, Integer> map) {
		return mapper.ejectMember(map);
	}

	public int createServer(String name, int memberNo) {
		// 이름으로 서버생성
		mapper.insertServer(name);
		// 방금 생성된 서버 번호 불러오기
		int serverNo = mapper.selectCreateServerNo();
		// 불러온 서버 번호에 관리자로 현재 사용자 등록하기
		HashMap<String, Integer>map = new HashMap<String, Integer>();
		map.put("memberNo", memberNo);
		map.put("serverNo", serverNo);

		mapper.insertServerMember(map);
		// 기본 채널 생성 (채팅, 보이스)
		mapper.insertDefaultTextChannel(serverNo);
		mapper.insertDefaultVoiceChannel(serverNo);
		return serverNo;
	}
	
	
	//
	public int insertChannel(Channel ch) {
		return mapper.insertChannel(ch);
	}

	public int editChannel(HashMap<String, String> map) {
		return mapper.editChannel(map);
	}

	public int deleteChannel(int channelNo) {
		return mapper.deleteChannel(channelNo);
	}
}
