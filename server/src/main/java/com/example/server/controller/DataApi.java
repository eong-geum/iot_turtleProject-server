package com.example.server.controller;

import com.example.server.dto.DataDto;
import com.example.server.service.DataApiService;
import com.example.server.service.FirebaseCloudMessageService;
import com.example.server.service.FirebaseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
public class DataApi {

    @Autowired
    FirebaseService firebaseService;

    @Autowired
    FirebaseCloudMessageService firebaseCloudMessageService;

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
        if(isTurtle.equals("Turtle"))
        {
            firebaseCloudMessageService.sendMessage(
                    "dQ75ycug00dpjeDL7Pk_XS:APA91bE748YQq7-USBd_qJptpaG6Mn52Nqy20w82ROWhfi3nhCY1lZcMe1Zxz3MRk7Xq-Bd3uoa5M_rYgypCcRTQZUO866PktzkbfydrIm_okqzSS0pASsxjxZxYJHMXfdDMei-lXbGX",
                    "안녕하세요! ","test 입니다.");
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
