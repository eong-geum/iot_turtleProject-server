package com.example.server.controller;

import com.example.server.dto.DataDto;
import com.example.server.service.DataApiService;
import com.example.server.service.FirebaseCloudMessageService;
import com.example.server.service.FirebaseService;
import com.example.server.service.FirebaseStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Data
public class DataApi {

    @Autowired
    FirebaseService firebaseService;

    @Autowired
    FirebaseCloudMessageService firebaseCloudMessageService;

    @Autowired
    FirebaseStorageService firebaseStorageService;

    private final DataApiService dataApiService = new DataApiService();
//    private final FirebaseController firebaseController;

    private DataDto data=new DataDto();

    // GET
    @GetMapping("/data")
    public String getData(){
        return dataApiService.getData();
//        System.out.println("get >>> "+ photo);
//        JsonObject jsonObject=new JsonObject();
//        jsonObject.addProperty("name",photo.getName());
//        jsonObject.addProperty("encodingContent",photo.getEncodingContent());
//        return jsonObject.toString();
    }

    public DataApi() {
        super();
    }

    //raspberryPI photo encoding -> spring loadbalancer -> opencv worker
    @PostMapping("/data")
    public void postData(@RequestBody DataDto photoDto) throws Exception {

        System.out.println("\n==== POST Request Received from raspberry PI : /data ====");
        // POST - raspberryPI photo encoding data
        // set encoding data
        data=dataApiService.setData(photoDto);

        // is it a turtle-neck?
        String isTurtle = dataApiService.getResult();
        List<String> registrationTokens = Arrays.asList(
                "dQ75ycug00dpjeDL7Pk_XS:APA91bE748YQq7-USBd_qJptpaG6Mn52Nqy20w82ROWhfi3nhCY1lZcMe1Zxz3MRk7Xq-Bd3uoa5M_rYgypCcRTQZUO866PktzkbfydrIm_okqzSS0pASsxjxZxYJHMXfdDMei-lXbGX",
                "cUG5VVCM—g3fTA4WuV_F7:APA91bEIBiSqcpLw6EZ72Xwmrwg83YEIybuzzYGDg2Ans3QL6izK77awbGE6UnzHzLYUv5xz1kvoP-3098SmRtXch131L9UfEoCYuusp9tynzwT__RI7unDr9HeeDQ2fhgEWdoB-2TA7"
        );
        String imageUrl="https://image.tmdb.org/t/p/w300/670x9sf0Ru8y6ezBggmYudx61yB.jpg";
        if(isTurtle.equals("Turtle"))
        {
            String uri =firebaseStorageService.uploadImage(photoDto.encodingContent);
            String imageUrl = "https://firebasestorage.googleapis.com/v0/b/turtleproject-2021.appspot.com/o/"+uri+"?alt=media";
            firebaseCloudMessageService.sendMessage(
                    "dQ75ycug00dpjeDL7Pk_XS:APA91bE748YQq7-USBd_qJptpaG6Mn52Nqy20w82ROWhfi3nhCY1lZcMe1Zxz3MRk7Xq-Bd3uoa5M_rYgypCcRTQZUO866PktzkbfydrIm_okqzSS0pASsxjxZxYJHMXfdDMei-lXbGX",
                    "안녕하세요! ","test 입니다.", imageUrl);
            String insertResult=firebaseService.insertData(data);
            //System.out.println("insert : "+insertResult);
        }

        /*
        if(isTurtle == "turtle"){

        }else if(isTurtle == "normal"){

        }*/

    }


    @GetMapping("/insertData")
    public String insertData(@RequestParam DataDto data) throws Exception {
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




}
