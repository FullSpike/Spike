package Package.mapper;

import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

public interface UserMapper {
    Integer studentLogin(@Param("number") String number, @Param("password") String password);

    String selectRoomByNumber(@Param("number") String number);

    void updateRoom(@Param("number") String number, @Param("room") String room);

    Integer selectIdByNumber(@Param("number") String number);

    void updatePassword(@Param("number") String number, @Param("newPassword") String newPassword);

    void updateOrderStatus(@Param("id") int id, @Param("status") String status, @Param("last_time") LocalDateTime last_time);

    void updateAdminPassword(@Param("number") String number, @Param("password") String password);

    Integer selectByNumber(@Param("number") String number);

    void registerStudent(@Param("number") String number, @Param("password") String password);

    int selectAdminByNumber(@Param("number") String number);

    void registerAdmin(@Param("number") String number, @Param("password") String password);

    Integer adminLogin(@Param("number") String number, @Param("password") String password);
}
