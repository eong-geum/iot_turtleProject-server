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

    // 유저 데이터 추가 (회원가입할 때 사용)
    @Override
    public String insertData(DataDto data) throws Exception {
        database = FirebaseDatabase.getInstance();
        //System.out.println(database.getReference());
        ref = database.getReference("users");

        //고유 아이디
        String id = "id_number123";
        DatabaseReference newUserRef = ref.child(id);

        //Map<String, FirebaseDataDto> firebaseData = new HashMap<>();
        //firebaseData.put(,new FirebaseDataDto("id",0,"kangho","Oct Wed ...", "image"));

        //회원 정보 초기화
        FirebaseDataDto firebaseDataDto = new FirebaseDataDto("id",0,"beomsic","Wed Oct ...", "image");

        //회원 추가
        newUserRef.setValueAsync(firebaseDataDto);

        return null;
    }

    @Override
    public DataDto selectData(String id) throws Exception {
        database = FirebaseDatabase.getInstance();
        //System.out.println(database.getReference());
        ref = database.getReference("users");

        DatabaseReference newref = ref.child(id);

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
