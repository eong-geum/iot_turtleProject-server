package com.example.server.service;

import com.example.server.api.PhotoAnalysisClient;
import com.example.server.dto.PhotoDto;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoApiService {

    private final PhotoAnalysisClient photoAnalysisClient;


    public String getResult(PhotoDto photoDto){
        String result="turtle";
        String analysResult=photoAnalysisClient.postAnalysis();
       return result;
    }


}
