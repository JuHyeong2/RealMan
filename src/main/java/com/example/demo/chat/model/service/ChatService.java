package com.example.demo.chat.model.service;

import com.example.demo.chat.model.vo.Channel;
import com.example.demo.chat.model.vo.Chat;
import org.springframework.stereotype.Service;

import com.example.demo.chat.model.mapper.ChatMapper;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ChatService {
	private final ChatMapper mapper;

    public ArrayList<Channel> chattingSidebar(int no) {
        return mapper.chattingSidebar(no);
    }
}
