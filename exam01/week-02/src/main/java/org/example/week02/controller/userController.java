package org.example.week02.controller;


import jakarta.validation.constraints.Pattern;
import org.example.week02.common.Result;
import org.example.week02.pojo.Order;
import org.example.week02.service.fixOrderService;
import org.example.week02.service.studentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/students")
@Validated

public class userController {

    @Autowired
    private studentService studentService;

    @Autowired
    private fixOrderService fixOrderService;

    @PostMapping()
    public Result<String> register(@Pattern(regexp = "^3(125|225)\\d{6}$") String number,
                                   @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z]).{6,}$") String password){
        studentService.registerStudent(number,password);
        return Result.success("register success");
    }

    @PutMapping("/{id}/room")
    public Result<String> updateRoom(@PathVariable long id,
                                     @RequestParam("room") String room){
        studentService.loginRoom(id,room);
        return Result.success("update success");
    }

    @PutMapping("/{id}/password")
    public Result<String> updatePassword(@PathVariable long id,
                                         @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z]).{6,}$") String password){
        studentService.updatePassword(id,password);
        return Result.success("update success");
    }

    @PostMapping("/{id}/order")
    public Result<String> addOrder(@PathVariable long id,
                                   @RequestParam("detail") String detail,
                                   @RequestParam("file") MultipartFile file){
        studentService.addOrder(id,detail,file);
        return Result.success("add success");
    }

    @GetMapping("/{id}/order")
    public Result<List<Order>> getOrder(@PathVariable long id){
        List<Order> o= fixOrderService.selectOrders(id);
        return Result.success(o);
    }

    @DeleteMapping("/{id}/order/{o_id}")
    public Result<String> deleteOrder(@PathVariable long id,
                                     @PathVariable long o_id){
        fixOrderService.deleteOrder(o_id);
        return Result.success("delete success");
    }
}
