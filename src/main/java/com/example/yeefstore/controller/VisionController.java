package com.example.yeefstore.controller;

import com.example.yeefstore.pojo.Vision;
import com.example.yeefstore.service.VisionService;
import com.example.yeefstore.utils.Result;
import com.example.yeefstore.utils.ResultFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin
@RestController
public class VisionController {
    @Resource
    private VisionService visionService;

    @PostMapping("/api/addvision")
    public Result addVision(@RequestBody Vision vision){
        try {
            int i = visionService.addVision(vision);
            if (i == 0){
                return ResultFactory.buildFailResult("记录数超过五个!");
            }else {
                return ResultFactory.buildSuccessResult(i);
            }
        }catch (Error error){
            return ResultFactory.buildFailResult(error.getMessage());
        }
    }

    @PostMapping("/api/getvision")
    public  Result getVision(@RequestBody Vision vision){
        return ResultFactory.buildSuccessResult(visionService.getVisions(vision));
    }
}
