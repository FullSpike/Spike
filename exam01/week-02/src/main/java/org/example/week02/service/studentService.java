package org.example.week02.service;



import javax.validation.constraints.Pattern;


public interface studentService {


    void  registerStudent(@Pattern(regexp = "^3(125|225)\\d{6}$") String number, @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z]).{6,}$") String password);
}
