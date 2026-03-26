package org.example.week02.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.week02.exception.serviceException;
import org.example.week02.mapper.adminMapper;
import org.example.week02.mapper.studentMapper;
import org.example.week02.pojo.Admin;
import org.example.week02.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import utils.JwtUtil;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private studentMapper studentMapper;

    @Autowired
    private adminMapper adminMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        try {
            Map<String, Object> map = JwtUtil.parseToken(token);
            String number= (String) map.get("number");
            String password = (String) map.get("password");
            if (number==null){
                throw new serviceException("请登录","401");
            }
            if(number.startsWith("3125")||number.startsWith("3225")){
                Student student = studentMapper.findStudentByNumber(number);
                if(!student.getPassword().equals(password)){
                    throw new serviceException("请登录","401");
                }
            }else if(number.startsWith("0025")){
                Admin admin=adminMapper.findAdminByNumber(number);
                if(!admin.getPassword().equals(password)){
                    throw new serviceException("请登录","401");
                }
            }
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }
}
