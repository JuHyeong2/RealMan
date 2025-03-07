package com.example.demo.chat.model.mapper;

import com.example.demo.chat.model.vo.Channel;
import com.example.demo.chat.model.vo.Chat;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ChatMapper {

    ArrayList<Channel> chattingSidebar(int no);
}
