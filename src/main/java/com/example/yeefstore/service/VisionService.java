package com.example.yeefstore.service;

import com.example.yeefstore.dao.VisionDao;
import com.example.yeefstore.pojo.Vision;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class VisionService {

    @Resource
    private VisionDao visionDao;

    public int addVision(Vision vision){
        LocalDate now = LocalDate.now();
        Example example = new Example(Vision.class);
        example.createCriteria().andEqualTo("user_id",vision.getUser_id());
        List<Vision> visions = visionDao.selectByExample(example);
        if (visions.size() >= 5){
            return 0;
        }else {
            vision.setCreate_time(now);
            int insert = visionDao.insert(vision);
            return insert;
        }
    }

    public List<Vision> getVisions(Vision vision){
        Example example = new Example(Vision.class);
        example.createCriteria().andEqualTo("user_id",vision.getUser_id());
        List<Vision> visions = visionDao.selectByExample(example);
        return visions;
    }

    public int deleteVision(Vision vision){
        return visionDao.delete(vision);
    }
}
