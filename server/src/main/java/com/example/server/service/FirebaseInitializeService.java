package com.example.server.service;

import java.io.FileInputStream;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
// Firebase에 접속하기 위한 설정 파일
public class FirebaseInitializeService {

	@PostConstruct
	public void initialize() {
		try {
			FileInputStream refreshToken = new FileInputStream(
				"./src/main/resources/turtleproject-2021-firebase-adminsdk-9tj4v-4d6deda7cb.json");

			FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(GoogleCredentials.fromStream(refreshToken))
				.setDatabaseUrl("https://turtleproject-2021-default-rtdb.firebaseio.com/")
				.setStorageBucket("turtleproject-2021.appspot.com")
				.build();

			FirebaseApp.initializeApp(options);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
