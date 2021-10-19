package com.example.server.controller;

import com.example.server.dto.PhotoDto;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhotoApi {

    private PhotoDto photo=new PhotoDto();

    @GetMapping("/photo")
    public String getPhoto(PhotoDto photoDto){
        System.out.println("get >>> "+ photo);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("name",photo.getName());
        jsonObject.addProperty("encodingContent",photo.getEncodingContent());
        return jsonObject.toString();
    }

    @PostMapping("/photo")
    public void postPhoto(@RequestBody PhotoDto photoDto){
        System.out.println("post >>>" + photoDto);
        photo.setName(photoDto.getName());
        photo.setEncodingContent(photoDto.getEncodingContent());
    }
}
