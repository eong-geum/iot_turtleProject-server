package com.example.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class FirebaseDataDto {
    private String id;
    private int count;
    private String name;
    private String date;
    private String encodingContent;
}
