package com.example.server.service;

import com.example.server.dto.DataDto;
import com.example.server.dto.FirebaseDataDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


@Service
public class FirebaseServiceImplement implements FirebaseService{

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private String currentSnapshot;

    // one instance, reuse
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    //objectMapper
    ObjectMapper objectMapper = new ObjectMapper();

    // 현재 저장된 count 가져오고, 만약 날짜가 바뀌었다면 /count에 count 저장 후 0으로 초기화
    public int getCount(String id) throws Exception {

        int ret = 0;

        HttpGet request = new HttpGet("https://turtleproject-2021-default-rtdb.firebaseio.com/users/" + id + ".json");

        try (CloseableHttpResponse response = httpClient.execute(request)){

            System.out.println("StatusCode = " + response.getStatusLine().toString());
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity);
            if(body.equals("null")){// 회원이 없는 경우
                System.out.println("새로운 회원");
                ret = 0;
            }
            else{
                System.out.println("회원 존재");
                FirebaseDataDto firebaseDataDto = objectMapper.readValue(body, FirebaseDataDto.class);
                ret = firebaseDataDto.getCount();

            }

        }
        return ret;
    }

    // firebase에 유저 데이터 insert, update
    @Override
    public String insertData(DataDto data) throws Exception {
        database = FirebaseDatabase.getInstance();
        //System.out.println(database.getReference());
        ref = database.getReference("users");

        // 사용자 정보 가져오기
        String id = data.getId();
        String name = data.getName();
        String encodingContent = data.getEncodingContent();
        String nowDate = data.getNowDate();
        String nowTime = data.getNowTime();

        //firebase에 있는 count 정보 가져오기 (REST API GET으로 가져올 예정)
        int count = getCount(id);

//        FirebaseDatabase database = newUserRef.getDatabase();
//        System.out.println("database = " + database);


        DatabaseReference newRef = ref.child(id);
        //회원 정보 초기화
        FirebaseDataDto firebaseDataDto = new FirebaseDataDto(id,count+1,name,nowDate,nowTime, encodingContent);

        //회원 추가
        newRef.setValueAsync(firebaseDataDto);

        return id;
    }

    @Override
    public DataDto selectData(String id) throws Exception {
        database = FirebaseDatabase.getInstance();
        //System.out.println(database.getReference());
        ref = database.getReference("users");

        DatabaseReference newref = ref.child(id);

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
