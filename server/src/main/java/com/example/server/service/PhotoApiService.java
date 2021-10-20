package com.example.server.service;

import com.example.server.dto.PhotoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PhotoApiService {

    private PhotoDto photo=new PhotoDto();

    // GET photo data
    public String getData(){
//        System.out.println("get >>> "+ photo);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("name",photo.getName());
        jsonObject.addProperty("encodingContent",photo.getEncodingContent());
        return jsonObject.toString();
    }

    // SET 인코딩한 photo data
    public void setData(PhotoDto photoDto){
//        System.out.println("post >>>" + photoDto);
        photo.setName(photoDto.getName());
        photo.setEncodingContent(photoDto.getEncodingContent());
    }

    // python 으로 POST
    public String getResult(){
        // result
        String result="turtle";

        // URL 설정
        String workerIP = "localhost";
        String workerPort = "5000";
        String method = "/opencv";
        String url = "http://"+workerIP+":"+workerPort+method;

        // Body 설정 (JSON 형태)
        JsonObject params=new JsonObject();
        params.addProperty("name",photo.getName());
        params.addProperty("encodingContent",photo.getEncodingContent());


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

        System.out.println("response = " + response);

       return result;
    }


}
