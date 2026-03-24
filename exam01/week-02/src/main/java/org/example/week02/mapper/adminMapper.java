package org.example.week02.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.week02.pojo.Admin;

public interface adminMapper {
    Admin findAdminByNumber(@Param("number") String number);

    void insertAdmin(@Param("number") String number,
                     @Param("password") String password);

    void updatePassword(long id, String password);

    Admin findAdminById(long id);
}
