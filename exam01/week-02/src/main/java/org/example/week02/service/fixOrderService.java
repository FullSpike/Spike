package org.example.week02.service;


import jakarta.validation.constraints.Pattern;
import org.example.week02.pojo.Order;

import java.util.List;

public interface fixOrderService {
    List<Order> selectOrders(long id);

    void deleteOrder(long oId);

    List<Order> getAllOrder();

    List<Order> getProcessOrder();

    List<Order> getUnProcessOrder();

    void updateOrder(long oId,String status);
}
