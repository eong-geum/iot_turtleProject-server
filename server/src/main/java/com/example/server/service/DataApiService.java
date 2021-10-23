package com.example.server.service;

import com.example.server.dto.DataDto;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class DataApiService {

    private DataDto data=new DataDto();

    // GET photo data
    public String getData(){
//        System.out.println("get >>> "+ photo);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("name",data.getName());
        jsonObject.addProperty("encodingContent",data.getEncodingContent());
        return jsonObject.toString();
    }

    // SET 인코딩한 photo data
    public DataDto setData(DataDto dataDto){
        System.out.println("set Data >>>" + dataDto);
        data.setId(dataDto.getId());
        data.setName(dataDto.getName());
        data.setNowDate(dataDto.getNowDate());
        data.setNowTime(dataDto.getNowTime());
        data.setEncodingContent(dataDto.getEncodingContent());
        return data;
    }

    // python 으로 POST
    public String getResult()  {
        // result
        String result="turtle";

        // URL 설정
        String workerIP = "localhost";
        String workerPort = "5000";
        String method = "/opencv";
        String url = "http://"+workerIP+":"+workerPort+method;

        // Body 설정 (JSON 형태)
        JsonObject params=new JsonObject();
        params.addProperty("name",data.getName());
        params.addProperty("nowDate",data.getNowDate());
        params.addProperty("nowTime",data.getNowTime());
        params.addProperty("encodingContent",data.getEncodingContent());


        // Header 설정 ,TYPE=json
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청하기 위해 header와 body 합치기
        HttpEntity<String> entity=new HttpEntity<String>(params.toString(),headers);

        // POST 요청
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );


        JsonParser jsonParser=new JsonParser();
        JsonObject jsonObject=(JsonObject) jsonParser.parse(response.getBody().toString());


        System.out.println("response = " + response.getBody());
//        System.out.println("json = "+ jsonObject);

//        String id=jsonObject.get("id").toString();
//        String name=jsonObject.get("user_name").toString();
//        String nowData=jsonObject.get("now_Date").toString();
//        String nowTime=jsonObject.get("now_Time").toString();
        String is_turtle=jsonObject.get("is_turtle").toString();

//        System.out.println("id : "+id);
//        System.out.println("name : "+name);
//        System.out.println("nowData : "+nowData);
//        System.out.println("nowTime : "+nowTime);
        System.out.println("is_turtle : "+is_turtle);

        result=is_turtle;

        return result;
    }


}
