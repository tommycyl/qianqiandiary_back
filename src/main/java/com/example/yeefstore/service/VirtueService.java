package com.example.yeefstore.service;

import com.example.yeefstore.dao.VirtueDao;
import com.example.yeefstore.dto.VirtueStatDto;
import com.example.yeefstore.pojo.Virtue;
import com.example.yeefstore.pojo.VirtueStat;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class VirtueService {
    @Resource
    private VirtueDao virtueDao;

    public int addVirtue(Virtue virtue) {
        LocalDate now = LocalDate.now();
        virtue.setDays(0);
        virtue.setCreate_time(now);
        return virtueDao.insert(virtue);
    }

    public int updateVirtue(Virtue virtue) {
        LocalDate now = LocalDate.now();
        LocalDate yestoday = now.minusDays(1);
        virtue.setCreate_time(yestoday);
        Virtue res = getVirtue(virtue);
        if (null == res){
            virtue.setDays(1);
        }else {
            virtue.setDays(res.getDays()+1);
        }
        Example example = new Example(Virtue.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("user_id", virtue.getUser_id());
        criteria.andEqualTo("create_time", now);
        virtue.setCreate_time(now);
//        更新所有实体中非null字段
        int i = virtueDao.updateByExampleSelective(virtue, example);
        return i;
    }

    public Virtue getVirtue(Virtue virtue) {
        return virtueDao.selectOne(virtue);
    }

    public VirtueStatDto<VirtueStat> getVirtueStat(Virtue virtue) {
//      获取全部打卡记录次数和全部打卡记录统计
        VirtueStatDto<VirtueStat> VirtueStatDto = new VirtueStatDto<VirtueStat>();
        List<VirtueStat> virtueStatlist = virtueDao.getVirtueStat(virtue.getUser_id());
        Integer total = 0;
        for (VirtueStat virtueStat : virtueStatlist) {
            total += virtueStat.getCount();
        }
        VirtueStatDto.setTotalCount(total);
        VirtueStatDto.setStats(virtueStatlist);

//      获取连续登录天数
        LocalDate now = LocalDate.now();
        LocalDate yestoday = LocalDate.now().minusDays(1);
        virtue.setCreate_time(now);
        Virtue todayRes = virtueDao.selectOne(virtue);
        virtue.setCreate_time(yestoday);
        Virtue yestodayRes = virtueDao.selectOne(virtue);
        if ((null == todayRes && null == yestodayRes) || (todayRes.getState() == 0 && yestodayRes.getState() == 0)) {
            VirtueStatDto.setDays(0);
        } else if (null != todayRes && null == yestodayRes){
            VirtueStatDto.setDays(todayRes.getDays());
        }else if (null == todayRes && null != yestodayRes){
            VirtueStatDto.setDays(yestodayRes.getDays());
        }else {
            VirtueStatDto.setDays(todayRes.getDays() == 0 ? yestodayRes.getDays() : todayRes.getDays());
        }

//      获取最大打卡天数
        Integer maxdays = virtueDao.getMaxdays(virtue.getUser_id());
        VirtueStatDto.setMaxDays(maxdays);

        return VirtueStatDto;
    }
}
