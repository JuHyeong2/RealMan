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

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

	public ArrayList<ChatMessage> selectChatList(int channelNo, String timeType) {

		Firestore db = FirestoreClient.getFirestore();

		// asynchronously retrieve all users
		ApiFuture<QuerySnapshot> query = db.collection("RealMan01").whereEqualTo("dc_no", channelNo)
				.orderBy("chat_createdate", Query.Direction.DESCENDING).get();
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
			String inputTime = String.valueOf(document.getTimestamp("chat_createdate"));
			// ISO 형태의 문자열을 ZonedDateTime으로 파싱
			ZonedDateTime dateTime = ZonedDateTime.parse(inputTime).withZoneSameInstant(ZoneId.of("Asia/Seoul"));
			// 24시간제 포맷 지정
			DateTimeFormatter formatter24 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			String formatted24 = dateTime.format(formatter24);
			// 12시간제 포맷 지정 (오전/오후 포함)
			DateTimeFormatter formatter12 = DateTimeFormatter.ofPattern("yyyy-MM-dd a h:mm");
			String formatted12 = dateTime.format(formatter12);
			ChatMessage message = new ChatMessage();
			message.setMessage(document.getString("chat_content"));
			message.setRoomId(document.getLong("dc_no").intValue());
			message.setSender(document.getString("chat_memberNickname"));
			message.setCreateDate(timeType.equals("24H")? formatted24:formatted12);
			message.setSeparetor(document.getString("chat_separator"));
//			message.setCreateDate(document.getTimestamp("chat_createdate").toString());
			chatList.add(message);
				System.out.println(timeType+ " : "+ message.getCreateDate());
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


	public int createDM(Map<String, Integer> map) {

		return mapper.createDM(map);
	}


	public void insertDM(DM message) {
		Firestore db = FirestoreClient.getFirestore();

		DocumentReference docRef = db.collection("RealMan01").document();
		System.out.println("ghkr"+ docRef);


		Map<String, Object> data = new HashMap<>();
		data.put("otherMemberNickname", message.getOtherMemberNickname());
		data.put("dm_no", message.getDmNo());
		data.put("sender", message.getSender());
		data.put("message", message.getMessage());
		data.put("dm_createdate", Timestamp.now());
		ApiFuture<WriteResult> result = docRef.set(data);

		System.out.println( "지금" + message.getSender());
		System.out.println( "지금" +  message.getMessage());
		System.out.println( "지금" +   message.getDmNo());

		try {
			System.out.println("Update timess : " + result.get().getUpdateTime());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public ArrayList<DM> selectDmList(int memberNo) {

		return mapper.selectDmList(memberNo);
	}

	public ArrayList<DM> selectDm(int dmNo, String timeType) {
		Firestore db = FirestoreClient.getFirestore();

		ApiFuture<QuerySnapshot> query = db.collection("RealMan01").whereEqualTo("dm_no", dmNo).orderBy("dm_createdate", Query.Direction.DESCENDING).get();
		// ...
		// query.get() blocks on response
		QuerySnapshot querySnapshot;
		ArrayList<DM> DMList = new ArrayList<DM>();
		try {
			querySnapshot = query.get();
			List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
			System.out.println("ehzb"+documents);
			for (QueryDocumentSnapshot document : documents) {
				String inputTime = String.valueOf(document.getTimestamp("dm_createdate"));
				// ISO 형태의 문자열을 ZonedDateTime으로 파싱
				ZonedDateTime dateTime = ZonedDateTime.parse(inputTime).withZoneSameInstant(ZoneId.of("Asia/Seoul"));
				// 24시간제 포맷 지정
				DateTimeFormatter formatter24 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				String formatted24 = dateTime.format(formatter24);
				// 12시간제 포맷 지정 (오전/오후 포함)
				DateTimeFormatter formatter12 = DateTimeFormatter.ofPattern("yyyy-MM-dd a h:mm");
				String formatted12 = dateTime.format(formatter12);

				System.out.println("document : "+document);
				DM message = new DM();
				message.setDmNo(document.getLong("dm_no").intValue());
				message.setOtherMemberNickname(document.getString("otherMemberNickname"));
				message.setSender(document.getString("sender"));
				message.setMessage(document.getString("message"));
				message.setDmCreateDate(timeType.equals("24H")? formatted24:formatted12);



				System.out.println("불러온 메시지: " + message.toString()); // 콘솔에서 데이터 확인



				DMList.add(message);
			}
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return DMList;
	}


	public DM findDMByMembers(int memberNo, int otherMemberNo) {
		return mapper.findDMByMembers(memberNo, otherMemberNo);
	}


	public DM selectDmUseNickname(HashMap<String, Integer> map) {
		return mapper.selectDmUseNickname(map);
	}


}

