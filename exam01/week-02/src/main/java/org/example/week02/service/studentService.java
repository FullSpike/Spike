package org.example.week02.service;



import jakarta.validation.constraints.Pattern;
import org.example.week02.pojo.Order;
import org.springframework.web.multipart.MultipartFile;


public interface studentService {


    void  registerStudent( String number,String password);

    void loginRoom(long id, String room);

    void updatePassword(long id, String password);

    void addOrder(long id, String details, MultipartFile file);

}
