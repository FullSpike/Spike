package org.example.week02.config;


import org.example.week02.interceptors.LoginInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    // 获取项目根目录
    ApplicationHome ap=new ApplicationHome(this.getClass());
    String path_name =ap.getDir().getParentFile().getParentFile()
            .getAbsolutePath();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*
        * 登录拦截器
        * */
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns("/students","/admins","/login","/order/**");
    }
    // 配置资源映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射 /order/** 到本地图片目录
        registry.addResourceHandler("/order/**")
                .addResourceLocations("file:"+path_name+"/src/main/resources/static/order/");
    }

}
