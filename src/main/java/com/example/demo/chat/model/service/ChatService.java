package com.example.demo.chat.model.service;

import com.example.demo.chat.model.vo.Channel;
import com.example.demo.chat.model.vo.ChatMessage;
import com.example.demo.chat.model.vo.DM;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import org.springframework.stereotype.Service;

import com.example.demo.chat.model.mapper.ChatMapper;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ChatService {
	private final ChatMapper mapper;

    public ArrayList<Channel> chattingSidebar(int no) {
        return mapper.chattingSidebar(no);
    }

	public void insertChat(ChatMessage message) {
		// 현재 날짜/시간        
		LocalDateTime now = LocalDateTime.now();         
		// 현재 날짜/시간 출력        
		System.out.println(now); 
		// 2021-06-17T06:43:21.419878100          
		// 포맷팅        
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"));         
		// 포맷팅 현재 날짜/시간 출력        
		System.out.println(formatedNow);  // 2021년 06월 17일 06시 43분 21초
		
		
		
		Firestore db = FirestoreClient.getFirestore();

		
		DocumentReference docRef = db.collection("RealMan01").document();
		
		
		// Add document data  with id "alovelace" using a hashmap
		Map<String, Object> data = new HashMap<>();
		data.put("chat_content", message.getMessage());
		data.put("chat_memberNickname", message.getSender());
		data.put("chat_createdate", Timestamp.now());
		data.put("dc_no", message.getRoomId());
		data.put("chat_separator", message.getSeparetor());
		//asynchronously write data
		ApiFuture<WriteResult> result = docRef.set(data);
		// ...
		// result.get() blocks on response
		try {
			System.out.println("Update time : " + result.get().getUpdateTime());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public ArrayList<ChatMessage> selectChatList(int channelNo) {
		Firestore db = FirestoreClient.getFirestore();

		// asynchronously retrieve all users
		ApiFuture<QuerySnapshot> query = db.collection("RealMan01").whereEqualTo("dc_no", channelNo).orderBy("chat_createdate", Query.Direction.DESCENDING).get();
		// ...
		// query.get() blocks on response
		QuerySnapshot querySnapshot;
		ArrayList<ChatMessage> chatList = new ArrayList<ChatMessage>();
		try {
			querySnapshot = query.get();
			List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot document : documents) {
//			  System.out.println("document: " + document.getId());
//			  System.out.println("chat_content: " + document.getString("chat_content"));
//			  System.out.println("chat_createdate: " + document.getTimestamp("chat_createdate"));
//			  System.out.println("chat_memberNickname: " + document.getString("chat_memberNickname"));
//			  System.out.println("chat_separator: " + document.getString("chat_separator"));
//			  System.out.println("dc_no: " + document.getLong("dc_no"));
//			  System.out.println();
			  ChatMessage message = new ChatMessage();
			  message.setMessage(document.getString("chat_content"));
			  message.setRoomId(document.getLong("dc_no").intValue());
			  message.setSender(document.getString("chat_memberNickname"));
//			  message.setCreateDate(document.getTimestamp("chat_createdate"));
			  message.setSeparetor(document.getString("chat_separator"));
			  chatList.add(message);
			}
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return chatList;
		
	}

	public Channel selectChannel(int channelNo) {
		
		return mapper.selectChannel(channelNo);
	}

    public ArrayList<DM> selectDm(int memberNo) {
		return mapper.selectDm(memberNo);
    }
}
