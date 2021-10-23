package com.example.server.service;

import com.example.server.dto.DataDto;
import com.example.server.dto.FirebaseDataDto;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.awt.image.DataBuffer;
import java.util.HashMap;
import java.util.Map;


@Service
public class FirebaseServiceImplement implements FirebaseService{

    private FirebaseDatabase database;
    private DatabaseReference ref;

    @Override
    public String insertData(DataDto data) throws Exception {
        database = FirebaseDatabase.getInstance();
        System.out.println(database.getReference());
        ref = database.getReference("userID");

        Map<String, FirebaseDataDto> firebaseData = new HashMap<>();
        firebaseData.put("qwe", new FirebaseDataDto("id",4,"kangho","Oct Wed ..."));

        ref.setValueAsync(firebaseData);
        ref.

        return null;
    }

    @Override
    public DataDto selectData(String id) throws Exception {
        return null;
    }

    @Override
    public String updateData(DataDto data) throws Exception {
        return null;
    }

    @Override
    public String deleteData(String id) throws Exception {
        return null;
    }
}
