package com.example.demo.chat.model.mapper;

import com.example.demo.chat.model.vo.Channel;
import com.example.demo.chat.model.vo.DM;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Mapper
public interface ChatMapper {

    ArrayList<Channel> chattingSidebar(int no);

	Channel selectChannel(int channelNo);

    ArrayList<DM> selectDmList(int memberNo);

    DM findDMByMembers(int memberNo, int otherMemberNo);

    int createDM(Map<String, Integer> map);

    DM selectDmUseNickname(HashMap<String, Integer> map);
}
