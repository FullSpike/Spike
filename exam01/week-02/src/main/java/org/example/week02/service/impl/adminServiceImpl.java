package org.example.week02.service.impl;

import org.example.week02.exception.serviceException;
import org.example.week02.mapper.adminMapper;
import org.example.week02.pojo.Admin;
import org.example.week02.pojo.Student;
import org.example.week02.service.adminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class adminServiceImpl implements adminService {

    @Autowired
    private adminMapper am;

    @Override
    public void registerAdmin(String number, String password) {
        Admin a = am.findAdminByNumber(number);
        if(a!=null){
            throw new serviceException("工号已存在","401");
        }
        am.insertAdmin(number, password);
    }

    @Override
    public void updatePassword(long id, String password) {
        if(am.findAdminById(id)==null){
            throw new serviceException("id不存在","401");
        }
        am.updatePassword(id, password);
    }
}
