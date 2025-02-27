package com.example.demo.server.model.service;


import com.example.demo.server.model.mapper.ServerMapper;
import com.example.demo.server.model.vo.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ServerService {
    private final ServerMapper mapper;

    public ArrayList<Server> serverList(int memberNo) {
        return mapper.serverList(memberNo);
    }
}
