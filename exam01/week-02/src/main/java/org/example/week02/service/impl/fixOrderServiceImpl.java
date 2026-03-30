package org.example.week02.service.impl;

import org.example.week02.exception.serviceException;
import org.example.week02.mapper.fixOrderMapper;
import org.example.week02.pojo.Order;
import org.example.week02.service.fixOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class fixOrderServiceImpl implements fixOrderService {

    @Autowired
    private fixOrderMapper fom;

    @Override
    public List<Order> selectOrders(long f_id) {
        return fom.selectOrdersByF_id(f_id);
    }

    @Override
    public void deleteOrder(long oId) {
        if(fom.selectOrderById(oId)==null) {
            throw new serviceException("订单不存在","404");
        }
        fom.deleteOrder(oId);
    }

    @Override
    public List<Order> getAllOrder() {
        return fom.selectAll();
    }

    @Override
    public List<Order> getProcessOrder() {
        return fom.selectProcessOrder();
    }

    @Override
    public List<Order> getUnProcessOrder() {
        return fom.selectUnProcessOrder();
    }

    @Override
    public void updateOrder(long oId,String status) {
        if(fom.selectOrderById(oId)==null) {
            throw new serviceException("订单不存在","404");
        }
        fom.updateOrder(oId,status, LocalDateTime.now());
    }
}
