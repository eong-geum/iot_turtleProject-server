package com.example.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.json.Json;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.net.http.HttpRequest;

@Component
@RequiredArgsConstructor
public class FirebaseCloudMessageService {
    private final String url="https://fcm.googleapis.com/vi/projects/turtleproject-2021/fcm/send";
    private final ObjectMapper objectMapper;

//
//    public void sendMessage(String token,String title,String body) throws FirebaseMessagingException, IOException {
//
////        String message=makeMessage(token,title,body);
////        OkHttpClient client=new OkHttpClient();
////        okhttp3.RequestBody requestBody=okhttp3.RequestBody.create(message, MediaType.get("application/json,charset=utf-8"));
////        Request request = new Request.Builder()
////                .url(url)
////                .post(requestBody)
////                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
////                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
////                .build();
////        Response response= client.newCall(request).execute();
////        System.out.println("=====Notification Response : "+response);
//
//        JsonObject notification=new JsonObject();
//        notification.addProperty("title",title);
//        notification.addProperty("body",body);
//
//        JsonObject message=new JsonObject();
//        message.addProperty("token",token);
//        message.add("notification",notification);
//
//        // Body 설정 (JSON 형태)
//        JsonObject params=new JsonObject();
////        params.addProperty("title",title);
////        params.addProperty("body",body);
//        params.add("notification",notification);
//
//
//        // Header 설정 ,TYPE=json
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add("Authorization", "Bearer " + token);
//
//        // 요청하기 위해 header와 body 합치기
//        HttpEntity<JsonObject> entity=new HttpEntity<JsonObject>(params,headers);
//
//        // POST 요청
//        RestTemplate template = new RestTemplate();
//        ResponseEntity<String> response = template.exchange(
//                url,
//                HttpMethod.POST,
//                entity,
//                String.class
//        );
//        //System.out.println("response.getBody() = " + response.getBody());
//
////        JsonParser jsonParser=new JsonParser();
////        JsonObject jsonObject=(JsonObject) jsonParser.parse(response.getBody()).getAsJsonObject();
//
//        System.out.println("====cloud message Response ==== "+ response.getStatusCode());
//
////        pushMessage(message);
//    }
//
//    public String makeMessage(String token,String title,String body) throws JsonProcessingException {
//
//        Notification notification=Notification.builder().setTitle(title).setBody(body).setImage(null).build();
//
//        Message message=Message.builder().setToken(token).setNotification(notification).build();
//        return objectMapper.writeValueAsString(message);
//    }
////    public String pushMessage(Message message) throws FirebaseMessagingException {
////        return instance.send(message);
////    }
    public void sendMessage(String token,String title,String body) throws FirebaseMessagingException {
        Notification notification=Notification.builder().setTitle(title).setBody(body).build();
        Message message= Message.builder()
                .putData("title",title)
                .putData("body",body)
                .setToken(token)
                .setNotification(notification)
                .build();

        String response = FirebaseMessaging.getInstance().send(message);

        System.out.println("=====Notification====="+response);
    }
}

