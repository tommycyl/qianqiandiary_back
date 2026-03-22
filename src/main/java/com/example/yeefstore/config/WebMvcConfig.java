package com.example.yeefstore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//配置拦截规则
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private JwtTokenInterceptor jwtTokenInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> list = new ArrayList<String>();
        registry.addInterceptor(jwtTokenInterceptor)
                // 拦截所有接口
                .addPathPatterns("/api/**")
                // 放行登录接口（不需要Token验证）
                .excludePathPatterns("/api/login","/api/regist","/api/wxlogin");
    }
}
