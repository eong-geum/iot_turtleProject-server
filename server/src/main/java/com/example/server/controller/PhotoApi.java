package com.example.server.controller;

import com.example.server.dto.PhotoDto;
import com.example.server.service.PhotoApiService;
import com.google.gson.JsonObject;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/photo")
    public void postPhoto(@RequestBody PhotoDto photoDto){
        System.out.println("post >>>" + photoDto);
        photo.setName(photoDto.getName());
        photo.setEncodingContent(photoDto.getEncodingContent());
        String result=photoApiService.getResult(photo);
    }
}
