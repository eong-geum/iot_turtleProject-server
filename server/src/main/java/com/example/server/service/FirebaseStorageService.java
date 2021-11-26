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

@Component
@RequiredArgsConstructor
public class FirebaseStorageService {

    public void uploadImage(String encodedImage){
        StorageClient storageClient = StorageClient.getInstance(FirebaseApp.getInstance());
        Bucket bucket = storageClient.bucket();
        System.out.println("bucket = " + bucket);
        Storage storage = bucket.getStorage();
        System.out.println("storage = " + storage);
        String blob_name = "my-blob-name";
        Blob blob = bucket.create(blob_name, encodedImage.getBytes(StandardCharsets.UTF_8), "image/jpeg");

        System.out.println("blob = " + blob);
    }
}
