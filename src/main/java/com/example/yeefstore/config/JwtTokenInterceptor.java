package com.example.yeefstore.config;

import com.example.yeefstore.utils.JwtUtil;
import com.example.yeefstore.utils.Result;
import com.example.yeefstore.utils.ResultFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
//配置拦截器，校验token
@CrossOrigin
@Component
public class JwtTokenInterceptor implements HandlerInterceptor {
    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        // 2. 验证Token是否存在
        if (token == null || token.isEmpty()) {
            returnJson(response, ResultFactory.buildResult(401,"token为空",null));
            return false;
        }

        // 3. 处理Bearer Token格式（可选，规范写法）
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // 4. 验证Token是否有效（包括签名和过期时间）
        try {
            if (!jwtUtil.validateToken(token)) {
                returnJson(response, ResultFactory.buildResult(401,"token过期！",null));
                return false;
            }
        } catch (Exception e) {
            returnJson(response, ResultFactory.buildFailResult("token解析失败"));
            return false;
        }

        // 5. Token有效，将用户名存入请求属性，方便后续接口使用
        String username = jwtUtil.getUsernameFromToken(token);
        request.setAttribute("username", username);

        // 6. 放行请求
        return true;
    }
    /**
     * 统一返回JSON格式的错误响应
     */
    private void returnJson(HttpServletResponse response, Result result) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401未授权

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);

        PrintWriter writer = response.getWriter();
        writer.print(json);
        writer.flush();
        writer.close();
    }
}
