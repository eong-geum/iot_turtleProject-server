package com.example.server.controller;

import com.example.server.dto.PhotoDto;
import com.example.server.service.PhotoApiService;
import com.google.gson.JsonObject;
import lombok.Data;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Data
public class PhotoApi {

    private final PhotoApiService photoApiService;

    private PhotoDto photo=new PhotoDto();

    @GetMapping("/photo")
    public String getPhoto(){
        System.out.println("get >>> "+ photo);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("name",photo.getName());
        jsonObject.addProperty("encodingContent",photo.getEncodingContent());
        return jsonObject.toString();
    }

    //raspberryPI photo encoding -> spring loadbalancer -> opencv worker
    @PostMapping("/photo")
    public void postPhoto(@RequestBody PhotoDto photoDto){
        System.out.println("post >>>" + photoDto);
        photo.setName(photoDto.getName());
        photo.setEncodingContent(photoDto.getEncodingContent());

        //  ========send to worker=========

        // URL 설정
        String workerIP = "localhost";
        String workerPort = "5000";
        String method = "/opencv";
        String url = "http://"+workerIP+":"+workerPort+method;

        // Body 설정
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("name", photo.getName());
        params.add("encodingContent", photo.getEncodingContent());

        // Header 설정
        HttpHeaders headers = new HttpHeaders();

        // 요청하기 위해 header와 body 합치기
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        // POST 요청
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response = template.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );
        System.out.println("response = " + response);
        /*
        if(response.getBody() == "turtle"){

        }else if(response.getBody() == "normal"){

        }*/

    }
}
