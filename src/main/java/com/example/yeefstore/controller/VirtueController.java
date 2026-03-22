package com.example.yeefstore.controller;

import com.example.yeefstore.dto.VirtueStatDto;
import com.example.yeefstore.pojo.Virtue;
import com.example.yeefstore.pojo.VirtueStat;
import com.example.yeefstore.service.VirtueService;
import com.example.yeefstore.utils.Result;
import com.example.yeefstore.utils.ResultFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin
@RestController
public class VirtueController {

    @Resource
    private VirtueService virtueService;

    @PostMapping("/api/addVirtue")
    public Result addVirtue(@RequestBody Virtue virtue) {
        Virtue resVirtue = virtueService.getVirtue(virtue);
        if (null == resVirtue) {
            int res = virtueService.addVirtue(virtue);
            return ResultFactory.buildSuccessResult(res);
        } else {
            return ResultFactory.buildFailResult("今天已有记录");
        }
    }

    @PostMapping("/api/getVirtue")
    public Result getVirtue(@RequestBody Virtue virtue) {
        Virtue resV = virtueService.getVirtue(virtue);
        if (null == resV){
            return ResultFactory.buildSuccessResult(resV);
        }else {
            return ResultFactory.buildSuccessResult(resV);
        }
    }

    @PostMapping("/api/updateVirtue")
    public Result updateVirtue(@RequestBody Virtue virtue) {

        try {
            int i = virtueService.updateVirtue(virtue);
            return ResultFactory.buildSuccessResult(i);
        } catch (Error error) {
            return ResultFactory.buildFailResult(error.getMessage());
        }
    }

    @PostMapping("/api/getVirtueStat")
    public Result getVirtueStat(@RequestBody Virtue virtue) {
        VirtueStatDto<VirtueStat> resVS = virtueService.getVirtueStat(virtue);
        return ResultFactory.buildSuccessResult(resVS);
    }
}
