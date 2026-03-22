package com.example.yeefstore.controller;

import com.example.yeefstore.dao.DiaryDao;
import com.example.yeefstore.dto.AllDay;
import com.example.yeefstore.dto.Oneday;
import com.example.yeefstore.dto.Stat;
import com.example.yeefstore.dto.StatDto;
import com.example.yeefstore.pojo.Diary;
import com.example.yeefstore.pojo.User;
import com.example.yeefstore.service.DiaryService;
import com.example.yeefstore.utils.Result;
import com.example.yeefstore.utils.ResultFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Stack;

@CrossOrigin
@RestController
public class DiaryController {
    @Resource
    private DiaryService diaryService;

    @Resource
    private DiaryDao diaryDao;

    @PostMapping("/api/addDiary")
    public Result addDiary(@RequestBody Diary diary){
        int res = diaryService.addDiary(diary);
        if (res == 0){
            return ResultFactory.buildFailResult("创建失败！");
        }else{
            return ResultFactory.buildSuccessResult(res);
        }
    }

    @GetMapping("/api/getDiary/{id}")
    public Result getDiary(@PathVariable("id") String id){
        return ResultFactory.buildSuccessResult(diaryDao.selectByPrimaryKey(id));
    }


    @PostMapping("/api/updateDiary")
    public Result updateDiary(@RequestBody Diary diary){
        diaryService.updateDiary(diary);
        return ResultFactory.buildSuccessResult("1");
    }

    @PostMapping("/api/deleteDiary")
    public Result deleteDiary(@RequestBody Diary diary){
        diaryService.deleteDiary(diary.getId());
        return ResultFactory.buildSuccessResult("1");
    }
    @PostMapping("/api/getAllDays")
    public Result getMonth(@RequestBody AllDay allDay){
        List<Integer> allDays = diaryService.getAllDays(allDay);
        return ResultFactory.buildSuccessResult(allDays);
    }

    @PostMapping("/api/getOnedayDiary")
    public Result getOnedayDiary(@RequestBody Oneday oneday){
        List<Diary> diary = diaryService.getDiary(oneday);
        return ResultFactory.buildSuccessResult(diary);
    }
    @PostMapping("/api/stat")
    public Result getStat(@RequestBody User user){
        StatDto res = diaryService.getStat(user);
        return ResultFactory.buildSuccessResult(res);
    }
}
