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
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FirebaseCloudMessageService {
    private final String url="https://fcm.googleapis.com/vi/projects/turtleproject-2021/fcm/send";
    private final ObjectMapper objectMapper;

    public void sendMessage(List<String> registrationTokens, String title, String body, String imageUrl) throws FirebaseMessagingException{

        Aps aps= Aps.builder().setSound("default").build();

        ApnsFcmOptions apnsFcmOptions=ApnsFcmOptions.builder().setImage(imageUrl).build();
        ApnsConfig apnsConfig= ApnsConfig.builder().setFcmOptions(apnsFcmOptions).setAps(aps).build();
        Notification notification=Notification.builder().setTitle(title).setBody(body).build();

        MulticastMessage message= MulticastMessage.builder()
                .addAllTokens(registrationTokens)
                .setApnsConfig(apnsConfig)
                .setNotification(notification)
                .build();

//        String registrationToken = token;
//        Message message=Message.builder()
//                .setToken(registrationToken)
//                .setApnsConfig(apnsConfig)
//                .setNotification(notification)
//                .build();

        BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
        if (response.getFailureCount() > 0) {
            List<SendResponse> responses = response.getResponses();
            List<String> failedTokens = new ArrayList<>();
//            System.out.println("=====Notification Response : ",response);
            for (int i = 0; i < responses.size(); i++) {
                if (!responses.get(i).isSuccessful()) {
                    // The order of responses corresponds to the order of the registration tokens.
                    failedTokens.add(registrationTokens.get(i));
                }
            }

            System.out.println("List of tokens that caused failures: " + failedTokens);
        }
    }

}

