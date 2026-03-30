package org.example.week02.service.impl;

import org.example.week02.exception.serviceException;
import org.example.week02.mapper.studentMapper;
import org.example.week02.pojo.Order;
import org.example.week02.pojo.Student;
import org.example.week02.service.studentService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class studentServiceImpl implements studentService {

    @Autowired
    private studentMapper sm;

    @Override
    public void registerStudent(String number, String password) {
        Student s = sm.findStudentByNumber(number);
        if(s!=null){
            throw new serviceException("学号已存在","401");
        }
        sm.insertStudent(number, password);
    }

    @Override
    public void loginRoom(long id, String room) {
        if(sm.findStudentById(id)==null){
            throw new serviceException("id不存在","401");
        }
        sm.updateRoom(id,room);
    }

    @Override
    public void updatePassword(long id, String password) {
        if(sm.findStudentById(id)==null){
            throw new serviceException("id不存在","401");
        }
        sm.updatePassword(id,password);
    }

    @Override
    public void addOrder(long id, String details, MultipartFile file) {
        Student s=sm.findStudentById(id);
        if(s==null){
            throw new serviceException("id不存在","401");
        }
        if(file.isEmpty()){
            throw new serviceException("文件为空","401");
        }
        String fileName=file.getOriginalFilename();
        String Uid= UUID.randomUUID().toString();
        ApplicationHome ap=new ApplicationHome(this.getClass());
        String path_name =ap.getDir().getParentFile().getParentFile()
                        .getAbsolutePath()+"//src//main//resources//static//order//"+
                Uid+
                fileName.substring(fileName.lastIndexOf("."));
        try {
            file.transferTo(new File(path_name));
        } catch (Exception e) {
            throw new serviceException("文件上传失败","401");
        }
        sm.addOrder(details,
                "未处理",
                id,
                s.getRoom(),
                s.getNumber(),
                LocalDateTime.now(),
                "http://localhost:8081/order/"+Uid+ fileName.substring(fileName.lastIndexOf(".")));
    }




}
