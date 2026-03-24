package org.example.week02.mapper;


import org.apache.ibatis.annotations.Param;
import org.example.week02.pojo.Order;

import java.util.List;

public interface fixOrderMapper {


    List<Order> selectOrdersByF_id(@Param("f_id") long f_id);


    void deleteOrder(@Param("id") long id);

    Order selectOrderById(@Param("id") long id);

    List<Order> selectAll();

    List<Order> selectProcessOrder();

    List<Order> selectUnProcessOrder();

    void updateOrder(@Param("id") long id,@Param("status") String status);
}



