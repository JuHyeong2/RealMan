package com.example.demo.serverMember.model.service;


import com.example.demo.serverMember.model.mapper.ServerMemberMapper;
import com.example.demo.serverMember.model.vo.ServerMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ServerMemberService {

    private final ServerMemberMapper mapper;

    public ArrayList<ServerMember> serverMemberList(int serverNo) {
        return mapper.serverMemberList(serverNo);
    }
}
