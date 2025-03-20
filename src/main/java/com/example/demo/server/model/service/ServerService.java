package com.example.demo.server.model.service;


import com.example.demo.member.model.vo.Member;
import com.example.demo.server.model.mapper.ServerMapper;
import com.example.demo.server.model.vo.Server;
import com.example.demo.serverMember.model.vo.ServerMember;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

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

}
