package org.example.week02.config;


import org.example.week02.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*
        * 登录拦截器
        * */
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns("/students","/admins","/studentlogin","/adminlogin");
    }
}
