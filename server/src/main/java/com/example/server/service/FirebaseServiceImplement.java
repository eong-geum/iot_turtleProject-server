package com.example.server.service;

import com.example.server.dto.DataDto;
import com.example.server.dto.FirebaseDataDto;
import com.google.firebase.database.*;
import org.springframework.stereotype.Service;



@Service
public class FirebaseServiceImplement implements FirebaseService{

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private String currentSnapshot;


    // firebase에 유저 데이터 insert, update
    @Override
    public String insertData(DataDto data) throws Exception {
        database = FirebaseDatabase.getInstance();
        //System.out.println(database.getReference());
        ref = database.getReference("users");

        // 사용자 정보 가져오기
        String id = data.getId();
        String name = data.getName();
        String encodingContent = data.getEncodingContent();
        String nowDate = data.getNowDate();

        //firebase에 있는 count 정보 가져오기 (REST API GET으로 가져올 예정)
        int count = 0;

//        FirebaseDatabase database = newUserRef.getDatabase();
//        System.out.println("database = " + database);


        DatabaseReference newRef = ref.child(id);
        //회원 정보 초기화
        FirebaseDataDto firebaseDataDto = new FirebaseDataDto(id,count,name,nowDate, encodingContent);

        //회원 추가
        newRef.setValueAsync(firebaseDataDto);

        return id;
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
