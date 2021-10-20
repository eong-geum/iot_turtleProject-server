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

    // GET
    @GetMapping("/photo")
    public String getPhoto(){
        return photoApiService.getData();
//        System.out.println("get >>> "+ photo);
//        JsonObject jsonObject=new JsonObject();
//        jsonObject.addProperty("name",photo.getName());
//        jsonObject.addProperty("encodingContent",photo.getEncodingContent());
//        return jsonObject.toString();
    }

    //raspberryPI photo encoding -> spring loadbalancer -> opencv worker
    @PostMapping("/photo")
    public void postPhoto(@RequestBody PhotoDto photoDto){

        System.out.println("POST API");
        // POST - raspberryPI photo encoding data
        // set encoding data
        photoApiService.setData(photoDto);

        // is it a turtle-neck?
        String isTurtle = photoApiService.getResult();

        /*
        if(isTurtle == "turtle"){

        }else if(isTurtle == "normal"){

        }*/

    }
}
