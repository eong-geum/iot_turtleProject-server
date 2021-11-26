package com.example.server.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

@Configuration
// Firebase에 접속하기 위한 설정 파일
public class FirebaseInitializeService {

    @PostConstruct
    public void initialize(){
        try{
            FileInputStream refreshToken = new FileInputStream("./src/main/resources/turtleproject-2021-firebase-adminsdk-9tj4v-4d6deda7cb.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setDatabaseUrl("https://turtleproject-2021-default-rtdb.firebaseio.com/")
                    .setStorageBucket("turtleproject-2021.appspot.com")
                    .build();

            FirebaseApp.initializeApp(options);

            //Bucket bucket = StorageClient.getInstance().bucket();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
