package org.example.week02.controller;


import org.example.week02.common.Result;
import org.example.week02.service.studentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/students")
public class userController {

    @Autowired
    private studentService studentService;

    @PostMapping()
    public Result<String> register(@Pattern(regexp = "^3(125|225)\\d{6}$") String number,
                                   @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z]).{6,}$") String password){
        studentService.registerStudent(number,password);
        return Result.success("register success");
    }

}
