package com.example.demo.chat.model.service;

import org.springframework.stereotype.Service;

import com.example.demo.chat.model.mapper.ChatMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
	private final ChatMapper mapper;
}
