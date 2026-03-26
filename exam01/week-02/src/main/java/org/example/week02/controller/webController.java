package org.example.week02.controller;


import jakarta.validation.constraints.Pattern;
import org.example.week02.common.Result;
import org.example.week02.mapper.adminMapper;
import org.example.week02.mapper.studentMapper;
import org.example.week02.pojo.Admin;
import org.example.week02.pojo.Student;
import org.example.week02.service.adminService;
import org.example.week02.service.studentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping()
@Validated
public class webController {

    @Autowired
    private studentService studentService;

    @Autowired
    private adminService adminService;

    @Autowired
    private studentMapper studentMapper;

    @Autowired
    private adminMapper adminMapper;

    /*
    * 学生登录接口
    * */
    @GetMapping("/studentlogin")
    public Result<Student> studentLogin(@Pattern(regexp = "^3(125|225)\\d{6}$") String number,
                                @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z]).{6,}$") String password){
        if(studentMapper.findStudentByNumber(number)==null){
            return Result.error("学生不存在","400");
        }
        if(!studentMapper.findStudentByNumber(number).getPassword().equals(password)){
            return Result.error("密码错误","400");
        }
        // 生成token
        Student student=studentMapper.findStudentByNumber(number);
        studentlogin(student);
        return Result.success(student);
    }

    /*
    * 管理员登录接口
    * */
    @GetMapping("/adminlogin")
    public Result<Admin> adminLogin(@Pattern(regexp = "^0025\\d{6}$") String number,
                                    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z]).{6,}$") String password){
        if(adminMapper.findAdminByNumber(number)==null){
            return Result.error("管理员不存在","400");
        }
        if(!adminMapper.findAdminByNumber(number).getPassword().equals(password)){
            return Result.error("密码错误","400");
        }
        // 生成token
        Admin admin=adminMapper.findAdminByNumber(number);
        adminlogin(admin);
        return Result.success(admin);
    }

    private void adminlogin(Admin admin) {
        Map<String, Object> claims=new HashMap<>();
        claims.put("number",admin.getNumber());
        claims.put("password",admin.getPassword());
        admin.setToken(JwtUtil.createToken(claims));
    }

    private void studentlogin(Student student) {
        Map<String, Object> claims=new HashMap<>();
        claims.put("number",student.getNumber());
        claims.put("password",student.getPassword());
        student.setToken(JwtUtil.createToken(claims));
    }
}
