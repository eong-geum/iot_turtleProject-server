package com.example.server.api;

import com.example.server.dto.PhotoDto;
import com.example.server.service.PhotoApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class PhotoAnalysisClient {


    private final RestTemplate analysisTemplate=new RestTemplate();
    private PhotoApiService photoApiService=new PhotoApiService();

    private String analysis_url="http://172.10.8.60:5000/data";
    private String url="http://172.10.8.60:8080/data";
    private String params=photoApiService.getData();

    HttpHeaders headers=new HttpHeaders();
//    HttpEntity<String> entity=new HttpEntity<>(headers);
    HttpEntity<MultiValueMap<String,String>> analysis_entity=new HttpEntity<>(params,headers);


    public PhotoDto getPhoto(){
        return getPhotoTemplate.exchange(url,HttpMethod.GET,entity,PhotoDto.class).getBody();
    }

    public void postPhoto(PhotoDto photoDto){
        postPhotoTemplate.exchange(url,HttpMethod.POST,entity,PhotoDto.class);
    }

    public void String (PhotoDto photoDto){
//        ResponseEntity<String> response=analysisTemplate.exchange(url,HttpMethod.POST,entity,PhotoDto.class);
        return analysisTemplate.exchange(analysis_url, HttpMethod.POST,entity,PhotoDto.class);
    }



}
