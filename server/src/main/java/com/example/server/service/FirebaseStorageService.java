package com.example.server.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.StorageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class FirebaseStorageService {

    public String uploadImage(String encodedImage){
        Bucket bucket = StorageClient.getInstance(FirebaseApp.getInstance()).bucket();
        String blob_name = "my-blob-name"; //파일 이름
        Blob blob = bucket.create(blob_name, Base64.getDecoder().decode(encodedImage), "image/jpeg");

        Blob blob1 = bucket.get(blob_name);
        System.out.println("blob1 = " + blob1);

        System.out.println("blob = " + blob);

        return blob_name;
    }
}
