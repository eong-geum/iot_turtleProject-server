package com.example.server.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;

@Configuration
// Firebase에 접속하기 위한 설정 파일
public class FirebaseInitializeService {

    @PostConstruct
    public void initialize(){
        try{
            FileInputStream refreshToken = new FileInputStream("./src/main/resources/turtleproject-2021-firebase-adminsdk-9tj4v-67c760c382.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setDatabaseUrl("https://turtleproject-2021-default-rtdb.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
