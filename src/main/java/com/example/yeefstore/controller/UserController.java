package com.example.yeefstore.controller;

import com.example.yeefstore.dto.UserDto;
import com.example.yeefstore.pojo.User;
import com.example.yeefstore.service.UserService;
import com.example.yeefstore.utils.Result;
import com.example.yeefstore.utils.ResultFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@CrossOrigin
@RestController
public class UserController {
    @Resource
    private UserService userService;

//    @PostMapping("/api/getUser")
//    public Result getUser(@Valid @RequestBody int id){
//        return ResultFactory.buildSuccessResult(userService.findById(id));
//    }

    @GetMapping("/api/{id}")
    public Result getUser(@PathVariable("id") Long id){
        User user = userService.findById(id);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        return  ResultFactory.buildSuccessResult(userDto);
    }

}
