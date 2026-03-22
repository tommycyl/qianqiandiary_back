package com.example.yeefstore.service;

import com.example.yeefstore.dao.UserDao;
import com.example.yeefstore.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
public class UserService {
    @Resource
    private UserDao userDao;

    public User findById(Long id){
        User user = userDao.selectByPrimaryKey(id);
        return user;
    }

    public User findByName(String userName){
        User user = userDao.findByName(userName);
        return user;
    }

    public User addUser(User user){
        UUID uuid = UUID.randomUUID();
        user.setId(uuid.toString());
        user.setCreate_time(new Date());
        user.setUpdate_time(new Date());
        int result = userDao.insert(user);
        return userDao.selectByPrimaryKey(uuid.toString());
    }

    public User findUser(User user){
        return userDao.selectOne(user);
    }
}
