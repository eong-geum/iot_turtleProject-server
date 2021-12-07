package com.example.server.service;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import com.example.server.dto.DataDto;
import com.example.server.dto.FirebaseCountDto;
import com.example.server.dto.FirebaseDataDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@Service
public class FirebaseServiceImplement implements FirebaseService {

	// one instance, reuse
	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	//objectMapper
	ObjectMapper objectMapper = new ObjectMapper();
	private FirebaseDatabase database;
	private DatabaseReference ref;
	private String currentSnapshot;

	// 현재 저장된 count 가져오고, 만약 날짜가 바뀌었다면 /count에 count 저장 후 0으로 초기화
	// 날이 바뀐 경우 count를 0부터 다시 세야함
	public int getCount(String id, String nowDate) throws Exception {

		int ret = 0;

		HttpGet request = new HttpGet("https://turtleproject-2021-default-rtdb.firebaseio.com/users/" + id + ".json");

		try (CloseableHttpResponse response = httpClient.execute(request)) {

			System.out.println("=== POST request send to Firebase : " + response.getStatusLine().toString() + " ===");
			HttpEntity entity = response.getEntity();
			String body = EntityUtils.toString(entity);
			if (body.equals("null")) {// 회원이 없는 경우
				System.out.println("새로운 회원 추가");
				ret = 0;
			} else {
				System.out.println("이미 존재하는 회원 업데이트");
				FirebaseDataDto firebaseDataDto = objectMapper.readValue(body, FirebaseDataDto.class);

				String databaseDate = firebaseDataDto.getDate();

				if (databaseDate.equals(nowDate)) {
					//날짜가 바뀌지 않음
					ret = firebaseDataDto.getCount();
				} else {
					//날짜가 바뀌었으니 count를 0부터 다시 셈
					ret = 0;
				}
			}

		} catch (Exception e) {
			System.out.println("Exception = " + e);
		}

		return ret;
	}

	public void updateCount(DatabaseReference ref) {
		System.out.println("접근 DB URL = " + ref);
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot snapshot) {

				// id, date, count 가져오기
				FirebaseDataDto value = snapshot.getValue(FirebaseDataDto.class);
				int count1 = value.getCount();
				String id1 = value.getId();
				String date = value.getDate();

				DatabaseReference countRef = database.getReference("count");
				countRef = countRef.child(id1);
				countRef = countRef.child(date);

				FirebaseCountDto data = new FirebaseCountDto(count1);

				countRef.setValueAsync(data);

			}

			@Override
			public void onCancelled(DatabaseError error) {

			}
		});
	}

	// 거북목이 감지되었을 때, firebase에 유저 데이터 insert, update
	@Override
	public String insertData(DataDto data) throws Exception {
		database = FirebaseDatabase.getInstance();
		//System.out.println(database.getReference());
		ref = database.getReference("users");

		// 사용자 정보 가져오기
		String id = data.getId();
		String name = data.getName();
		String encodingContent = data.getEncodingContent();
		String nowDate = data.getNowDate(); //오늘 날짜
		String nowTime = data.getNowTime();

		// firebase에 있는 count 정보 가져오기 (REST API GET으로 가져올 예정)
		// 날짜가 바뀐 경우 count를 0부터 다시 세야한다.
		int count = getCount(id, nowDate);

		//newRef = [firebase]/users/{id}
		DatabaseReference newRef = ref.child(id);

		//회원 정보 초기화
		FirebaseDataDto firebaseDataDto = new FirebaseDataDto(id, count + 1, name, nowDate, nowTime, encodingContent);

		//회원 추가
		newRef.setValueAsync(firebaseDataDto);

		// 해당 회원의 {날짜 : count} firebase 업데이트
		updateCount(newRef);

		return id;
	}

	@Override
	public DataDto selectData(String id) throws Exception {
		return null;
	}

	@Override
	public String updateData(DataDto data) throws Exception {
		return null;
	}

	@Override
	public String deleteData(String id) throws Exception {
		return null;
	}
}
