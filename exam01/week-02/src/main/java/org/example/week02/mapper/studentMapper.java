package org.example.week02.mapper;


import org.apache.ibatis.annotations.Param;
import org.example.week02.pojo.Student;

public interface studentMapper {
    Student findStudentByNumber(@Param("number") String number);

    void insertStudent(@Param("number") String number, @Param("password") String password);
}
