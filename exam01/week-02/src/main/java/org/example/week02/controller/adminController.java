package org.example.week02.controller;


import jakarta.validation.constraints.Pattern;
import org.example.week02.common.Result;
import org.example.week02.pojo.Order;
import org.example.week02.service.adminService;
import org.example.week02.service.fixOrderService;
import org.example.week02.service.studentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
@Validated
public class adminController {

    @Autowired
    private fixOrderService fixOrderService;

    @Autowired
    private adminService adminService;


    @PostMapping()
    public Result<String> register(@Pattern(regexp = "^0025\\d{6}$") String number,
                                   @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z]).{6,}$") String password){
        adminService.registerAdmin(number,password);
        return Result.success("register success");
    }

    @GetMapping("/allorder")
    public Result<List<Order>> getAllOrder(){
        return Result.success(fixOrderService.getAllOrder());
    }
    @GetMapping("/processorder")
    public Result<List<Order>> getProcessOrder(){
        return Result.success(fixOrderService.getProcessOrder());
    }
    @GetMapping("/unprocessorder")
    public Result<List<Order>> getUnProcessOrder(){
        return Result.success(fixOrderService.getUnProcessOrder());
    }

    /*
    * 修改密码
    * */
    @PutMapping("/{id}/password")
    public Result<String> updatePassword(@PathVariable long id,
                                         @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z]).{6,}$") String password){
        adminService.updatePassword(id,password);
        return Result.success("update success");
    }

    @DeleteMapping("/{id}/order/{o_id}")
    public Result<String> deleteOrder(@PathVariable long id,
                                     @PathVariable long o_id){
        fixOrderService.deleteOrder(o_id);
        return Result.success("delete success");
    }

    @PutMapping("/{id}/order/{o_id}")
    public Result<String> updateOrder(@PathVariable long id,
                                     @PathVariable long o_id,
                                     @RequestParam("status") String status){
        fixOrderService.updateOrder(o_id,status);
        return Result.success("update success");
    }



}
