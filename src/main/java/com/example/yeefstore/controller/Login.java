package com.example.yeefstore.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.yeefstore.dto.UserDto;
import com.example.yeefstore.dto.WxLoginDto;
import com.example.yeefstore.pojo.User;
import com.example.yeefstore.service.UserService;
import com.example.yeefstore.utils.JwtUtil;
import com.example.yeefstore.utils.Result;
import com.example.yeefstore.utils.ResultFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
@CrossOrigin
@RestController
public class Login {
    @Resource
    private UserService userService;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private UserDto userDto;

    @Value("${wx.miniprogram.appid}")
    private String appid;
    @Value("${wx.miniprogram.secret}")
    private String secret;
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/api/login")
    public Result login(@RequestBody User user){
        User reUser = userService.findByName(user.getUsername());
        if (reUser == null){
            return ResultFactory.buildResult(401,"用户名错误",null);
        }
        if (reUser.getPassword().equals(user.getPassword())){
            String token = jwtUtil.generateToken(user.getUsername());
            userDto.setToken(token);
//          签发token拷贝签发
            BeanUtils.copyProperties(reUser,userDto);
            return ResultFactory.buildResult(200,"登陆成功",userDto);
        }else {
            return ResultFactory.buildResult(401,"密码错误",null);
        }
    }

    @PostMapping("/api/wxlogin")
    public Result wxLogin(@RequestBody WxLoginDto wxLoginDto){
        String openId = getOpenId(wxLoginDto.getCode());
        User user = new User();
        UserDto dto = new UserDto();

        user.setOpen_id(openId);
        User resUser = userService.findUser(user);

        if (null != resUser){
            String token = jwtUtil.generateToken(resUser.getUsername());
            dto.setToken(token);
            BeanUtils.copyProperties(resUser, dto);
            return ResultFactory.buildSuccessResult(dto);
        }else {
            user.setUsername("wx_default");
            user.setPassword("123456defa");
            User reUser = userService.addUser(user);
            String token = jwtUtil.generateToken(reUser.getUsername());
            dto.setToken(token);
            BeanUtils.copyProperties(reUser, dto);
            return ResultFactory.buildSuccessResult(dto);
        }
    }

    private String getOpenId(String code) {
        // 1. 拼接微信接口 URL
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid +
                "&secret=" + secret +
                "&js_code=" + code +
                "&grant_type=authorization_code";

        // 2. 发起 GET 请求
        String responseS = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = JSON.parseObject(responseS);

        // 3. 校验返回结果
        if (jsonObject.containsKey("errcode") && jsonObject.getInteger("errcode") != 0) {
            throw new RuntimeException("微信登录失败: " + jsonObject.getString("errmsg"));
        }

        return jsonObject.getString("openid");
    }
}
