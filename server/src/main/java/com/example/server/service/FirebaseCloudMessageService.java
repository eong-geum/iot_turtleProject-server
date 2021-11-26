package com.example.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.json.Json;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.*;
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

    public void sendMessage(String token,String title,String body, String imageUrl) throws FirebaseMessagingException{

        Aps aps= Aps.builder().setSound("default").build();
        ApnsFcmOptions apnsFcmOptions = ApnsFcmOptions.builder().setImage(imageUrl).build();
        ApnsConfig apnsConfig= ApnsConfig.builder().setAps(aps).setFcmOptions(apnsFcmOptions).build();
        Notification notification=Notification.builder().setTitle(title).setBody(body).setBody(imageUrl).build();

        String registrationToken = token;
        Message message=Message.builder()
                .setToken(registrationToken)
                .setApnsConfig(apnsConfig)
                .setNotification(notification)
                .build();

        String response = FirebaseMessaging.getInstance().send(message);

        System.out.println("=====Notification====="+response);
    }
}

