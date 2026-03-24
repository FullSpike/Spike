package org.example.week02.mapper;


import org.apache.ibatis.annotations.Param;
import org.example.week02.pojo.Student;

import java.time.LocalDateTime;

public interface studentMapper {
    Student findStudentByNumber(@Param("number") String number);

    void insertStudent(@Param("number") String number, @Param("password") String password);

    void updateRoom(@Param("id") long id, @Param("room") String room);

    Student findStudentById(long id);

    void updatePassword(long id, String password);

    void addOrder(@Param("detail") String detail,
                  @Param("status") String status,
                  @Param("f_id") long f_id,
                  @Param("room") String room,
                  @Param("number") String number,
                  @Param("last_time") LocalDateTime last_time,
                  @Param("path_name") String path_name);
}
