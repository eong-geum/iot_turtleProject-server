package com.example.server.service;

import com.example.server.dto.DataDto;

public interface FirebaseService {

	public String insertData(DataDto data) throws Exception;

	public DataDto selectData(String id) throws Exception;

	public String updateData(DataDto data) throws Exception;

	public String deleteData(String id) throws Exception;

}
