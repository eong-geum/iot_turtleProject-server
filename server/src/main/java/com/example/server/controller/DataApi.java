package com.example.server.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.dto.DataDto;
import com.example.server.service.DataApiService;
import com.example.server.service.FirebaseCloudMessageService;
import com.example.server.service.FirebaseService;
import com.example.server.service.FirebaseStorageService;
import com.google.firebase.messaging.FirebaseMessagingException;

import lombok.Data;

@RestController
@Data
public class DataApi {

	private final DataApiService dataApiService = new DataApiService();
	@Autowired
	FirebaseService firebaseService;
	@Autowired
	FirebaseCloudMessageService firebaseCloudMessageService;
	@Autowired
	FirebaseStorageService firebaseStorageService;
	//    private final FirebaseController firebaseController;
	private DataDto data = new DataDto();

	public DataApi() {
		super();
	}

	// GET
	@GetMapping("/data")
	public String getData() {
		return dataApiService.getData();
	}

	//raspberryPI photo encoding -> spring loadbalancer -> opencv worker
	@PostMapping("/data")
	public void postData(@RequestBody DataDto photoDto) throws Exception {

		System.out.println("\n==== POST Request Received from raspberry PI : /data ====");
		// POST - raspberryPI photo encoding data
		// set encoding data
		data = dataApiService.setData(photoDto);

		// is it a turtle-neck?
		String isTurtle = dataApiService.getResult();
		if (isTurtle.equals("Turtle")) {
			insertData(data);
			sendAlarm(photoDto);
		}

	}

	private void sendAlarm(DataDto photoDto) throws FirebaseMessagingException {
		List<String> registrationTokens = Arrays.asList(
			"dQ75ycug00dpjeDL7Pk_XS:APA91bE748YQq7-USBd_qJptpaG6Mn52Nqy20w82ROWhfi3nhCY1lZcMe1Zxz3MRk7Xq-Bd3uoa5M_rYgypCcRTQZUO866PktzkbfydrIm_okqzSS0pASsxjxZxYJHMXfdDMei-lXbGX",
			"cUG5VVCM—g3fTA4WuV_F7:APA91bEIBiSqcpLw6EZ72Xwmrwg83YEIybuzzYGDg2Ans3QL6izK77awbGE6UnzHzLYUv5xz1kvoP-3098SmRtXch131L9UfEoCYuusp9tynzwT__RI7unDr9HeeDQ2fhgEWdoB-2TA7"
		);
		String uri = firebaseStorageService.uploadImage(photoDto.encodingContent);
		String imageUrl =
			"https://firebasestorage.googleapis.com/v0/b/turtleproject-2021.appspot.com/o/" + uri + "?alt=media";
		firebaseCloudMessageService.sendMessage(
			registrationTokens,
			"거북목 발견!", "스트레칭 하세요", imageUrl);
	}

	public String insertData(DataDto data) throws Exception {
		System.out.println("hello");
		return firebaseService.insertData(data);
	}

	@GetMapping("/selectData")
	public DataDto selectData(@RequestParam String id) throws Exception {
		return firebaseService.selectData(id);
	}

	@GetMapping("/updateData")
	public String updateData(@RequestParam DataDto data) throws Exception {
		return firebaseService.updateData(data);
	}

	@GetMapping("/deleteData")
	public String deleteData(@RequestParam String id) throws Exception {
		return firebaseService.deleteData(id);
	}

	@GetMapping("/")
	public String home(){
		return "Hello World!";
	}

}
