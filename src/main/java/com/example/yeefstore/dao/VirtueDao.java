package com.example.yeefstore.dao;

import com.example.yeefstore.pojo.Virtue;
import com.example.yeefstore.pojo.VirtueStat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VirtueDao extends tk.mybatis.mapper.common.Mapper<Virtue> {
    @Select("select type , count(*) as count from virtue where user_id = #{user_id} AND state = 1 group by type")
    public List<VirtueStat> getVirtueStat(@Param("user_id")String user_id);

    @Select("select MAX(DISTINCT days) from virtue where user_id = #{user_id}")
    public Integer getMaxdays(@Param("user_id")String user_id);
}
