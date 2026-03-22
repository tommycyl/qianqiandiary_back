package com.example.yeefstore.dao;

import com.example.yeefstore.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao extends tk.mybatis.mapper.common.Mapper<User> {
    @Select("select * from user where username = #{userName}")
    public User findByName(@Param("userName") String userName);
}
