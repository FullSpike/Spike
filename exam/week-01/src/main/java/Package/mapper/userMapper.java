package Package.mapper;

import Package.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface userMapper {

    //增加
    void registerStudent(@Param("number") String number, @Param("password") String password);

    void addOrder(@Param("detail") String detail, @Param("status") String status, @Param("f_id") Integer f_id, @Param("room") String room, @Param("number") String number, @Param("last_time") LocalDateTime last_time);

    void registerAdmin(@Param("number") String number, @Param("password") String password);
    //查询
    Integer studentLogin(@Param("number") String number, @Param("password") String password);

    Integer adminLogin(@Param("number") String number, @Param("password") String password);

    Integer selectStudentByNumber(@Param("number") String number);

    String selectDormitoryByNumber(@Param("number") String number);

    Integer selectIdByNumber(@Param("number") String number);

    List<Order> selectOrderByF_id(@Param("f_id") Integer f_id);

    Integer selectAdminByNumber(@Param("number") String number);

     Order selectAdminOrderById(@Param("id") Integer id);

    List<Order> selectOrderAll();

    List<Order> selectProcessedOrders();

    List<Order> selectUnprocessedOrders();
    //更新
    void updateDormitory(@Param("number") String number, @Param("room") String room);

    void updatePassword(@Param("number") String number, @Param("password") String password);

    void updateEvaluation(@Param("detail") String detail, @Param("evaluation") String evaluation);

    void updateOrderStatus(@Param("id") Integer id, @Param("status") String status, @Param("last_time") LocalDateTime last_time);

    void updateAdminPassword(@Param("number") String number, @Param("password") String password);

    //删除
    void delOrder(@Param("detail") String detail, @Param("f_id") Integer f_id);

    void delAdminOrder(@Param("id") Integer id);
}
