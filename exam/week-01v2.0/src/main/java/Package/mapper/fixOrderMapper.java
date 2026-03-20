package Package.mapper;

import Package.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface fixOrderMapper {

    void addOrder(@Param("detail") String detail, @Param("status") String status,
                  @Param("f_id") int f_id, @Param("room") String room, @Param("number") String number, @Param("lastTime") LocalDateTime lastTime);

    List<Order> selectOrderByF_id(@Param("f_id") int f_id);

    void delOrder(@Param("id") int id);

    void updateEvaluation(@Param("id") int id, @Param("evaluation") String evaluation);

    List<Order> selectAllOrders();

    List<Order> selectProcessedOrders();

    List<Order> selectUnprocessedOrders();

    boolean selectOrderById(@Param("id") int id);
}
