package com.example.server.service;

import com.example.server.dto.DataDto;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.*;
import org.springframework.stereotype.Service;

import javax.print.Doc;


@Service
public class FirebaseServiceImplement implements FirebaseService{

    public static final String COLLECTION_NAME="test";

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("");

    @Override
    public String insertData(DataDto data) throws Exception {
        Firestore firestore= FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture=
                firestore.collection(COLLECTION_NAME).document(data.getId()).set(data);
        return apiFuture.get().getUpdateTime().toString();
    }

    @Override
    public DataDto selectData(String id) throws Exception {
        Firestore firestore= FirestoreClient.getFirestore();
        DocumentReference documentReference=
                firestore.collection(COLLECTION_NAME).document(id);
        ApiFuture<DocumentSnapshot> apiFuture=documentReference.get();
        DocumentSnapshot documentSnapshot=apiFuture.get();
        DataDto dataDto=null;
        if(documentSnapshot.exists()){
            dataDto=documentSnapshot.toObject(DataDto.class);
            return dataDto;
        }
        else{
            return null;
        }

    }

    @Override
    public String updateData(DataDto data) throws Exception {
        Firestore firestore=FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture=firestore.collection(COLLECTION_NAME)
                .document(data.getId()).set(data);
        return apiFuture.get().getUpdateTime().toString();
    }

    @Override
    public String deleteData(String id) throws Exception {
        Firestore firestore=FirestoreClient.getFirestore();
        ApiFuture<WriteResult> apiFuture=
                firestore.collection(COLLECTION_NAME).document(id).delete();

        return "Document id : " + id +" delete!";
    }
}
