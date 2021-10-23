package com.example.server.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DataDto {
    public String id;
    public String name;
    public String nowDate;
    public String nowTime;
    public String encodingContent;
}
