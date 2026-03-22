package com.example.yeefstore.controller;

import com.example.yeefstore.dao.UserDao;
import com.example.yeefstore.dto.UserDto;
import com.example.yeefstore.pojo.User;
import com.example.yeefstore.service.UserService;
import com.example.yeefstore.utils.JwtUtil;
import com.example.yeefstore.utils.Result;
import com.example.yeefstore.utils.ResultFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.datasource.JdbcTransactionObjectSupport;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
public class Regist {
    @Resource
    private UserService userService;
    @Resource
    private JwtUtil jwtUtil;

    @PostMapping("/api/regist")
    public Result Regist(@RequestBody User user){
        User reUser = userService.addUser(user);
        UserDto dto = new UserDto();
        String token = jwtUtil.generateToken(reUser.getUsername());
        dto.setToken(token);
        BeanUtils.copyProperties(reUser, dto);
        return ResultFactory.buildSuccessResult(dto);
    }

}
