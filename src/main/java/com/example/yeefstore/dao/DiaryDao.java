package com.example.yeefstore.dao;

import com.example.yeefstore.dto.AllDay;
import com.example.yeefstore.dto.Stat;
import com.example.yeefstore.pojo.Diary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Mapper
public interface DiaryDao extends tk.mybatis.mapper.common.Mapper<Diary> {

    @Select("select MAX(DISTINCT days) from diary where user_id = #{user_id} group by type")
    public int getMax(@Param("user_id") String user_id);

    @Select("select type , COUNT(*) as count from diary where user_id = #{user_id} group by type")
    public List<Stat> getStat(@Param("user_id") String user_id);

    @Select("select * from diary " +
            "where user_id = #{user_id} AND DATE(create_time) = #{create_time} " +
            "limit 5")
    public List<Diary> getDiary(@Param("user_id") String user_id,
                                @Param("create_time") LocalDate create_time);

    @Update("UPDATE diary " +
            "SET content = #{content} , title = #{title} , type = #{type} " +
            "where id = #{id}")
    public void update(
            @Param("id") String id,
            @Param("content") String content,
            @Param("title") String title,
            @Param("type") Long type
    );
    @Select("SELECT DISTINCT DATE(create_time) AS dd FROM diary " +
            "WHERE user_id = #{user_id} AND create_time >= #{start} " +
            "AND create_time <= #{end} " +
            "ORDER BY dd ASC ")
    public List<Date> getAllDays(@Param("user_id")String user_id,
                                 @Param("start") String start,
                                 @Param("end") String end);

}
