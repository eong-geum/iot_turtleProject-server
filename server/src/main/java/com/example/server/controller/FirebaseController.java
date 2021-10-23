//package com.example.server.controller;
//
//import com.example.server.dto.DataDto;
//import com.example.server.service.FirebaseService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class FirebaseController {
//
//    @Autowired
//    FirebaseService firebaseService;
//    @GetMapping("/insertData")
//    public String insertData(@RequestParam DataDto data) throws Exception {
//        return firebaseService.insertData(data);
//    }
//
//    @GetMapping("/selectData")
//    public DataDto selectData(@RequestParam String id) throws Exception {
//        return firebaseService.selectData(id);
//    }
//
//    @GetMapping("/updateData")
//    public String updateData(@RequestParam DataDto data) throws Exception {
//        return firebaseService.updateData(data);
//    }
//
//    @GetMapping("/deleteData")
//    public String deleteData(@RequestParam String id) throws Exception {
//        return firebaseService.deleteData(id);
//    }
//
//
//
//}
