package org.example.week02.service.impl;

import org.example.week02.exception.serviceException;
import org.example.week02.mapper.studentMapper;
import org.example.week02.pojo.Student;
import org.example.week02.service.studentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class studentServiceImpl implements studentService {

    @Autowired
    private studentMapper sm;

    @Override
    public void registerStudent(String number, String password) {
        Student s = sm.findStudentByNumber(number);
        if(s!=null){
            throw new serviceException("学号已存在");
        }
        sm.insertStudent(number, password);
    }
}
