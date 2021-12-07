package com.example.server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.ApnsFcmOptions;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FirebaseCloudMessageService {

	public void sendMessage(List<String> registrationTokens, String title, String body, String imageUrl) throws
		FirebaseMessagingException {

		Aps aps = Aps.builder().setSound("default").build();

		ApnsFcmOptions apnsFcmOptions = ApnsFcmOptions.builder().setImage(imageUrl).build();
		ApnsConfig apnsConfig = ApnsConfig.builder().setFcmOptions(apnsFcmOptions).setAps(aps).build();
		Notification notification = Notification.builder().setTitle(title).setBody(body).build();

		MulticastMessage message = MulticastMessage.builder()
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

