package com.example.demo.serverMember.model.mapper;

import com.example.demo.serverMember.model.vo.ServerMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ServerMemberMapper {
    ArrayList<ServerMember> serverMemberList(int serverNo);
}
