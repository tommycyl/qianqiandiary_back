package com.example.yeefstore.service;

import com.example.yeefstore.dao.DiaryDao;
import com.example.yeefstore.dto.AllDay;
import com.example.yeefstore.dto.Oneday;
import com.example.yeefstore.dto.Stat;
import com.example.yeefstore.dto.StatDto;
import com.example.yeefstore.pojo.Diary;
import com.example.yeefstore.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DiaryService {
    @Resource
    private DiaryDao diaryDao;

    public int addDiary(Diary diary){
//      查询昨天有没有记录
        LocalDate now = LocalDate.now();
        Oneday oneday = new Oneday(diary.getUser_id(), now);
        List<Diary> diaryList = getDiary(oneday);
        if (diaryList.isEmpty()){
            diary.setDays(1L);
        }else {
            diary.setDays(diaryList.get(0).getDays()+1);
        }
//正常插入逻辑
        UUID uuid = UUID.randomUUID();
        diary.setId(uuid.toString());
        diary.setCreate_time(LocalDate.now());
        int result = diaryDao.insert(diary);
        return result;
    }

    public List<Diary> getDiary(Oneday oneday){
        List<Diary> diary = diaryDao.getDiary(oneday.getUser_id(), oneday.getCreate_time());
        return diary;
    }

    public void updateDiary(Diary diary){
        diaryDao.update(diary.getId(), diary.getContent(), diary.getTitle(), diary.getType());
    }

    public void deleteDiary(String id){
        int res = diaryDao.deleteByPrimaryKey(id);
    }

    public List<Integer> getAllDays(AllDay allDay){
        String create_time = allDay.getCreate_time();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth yearMonth = YearMonth.parse(create_time,formatter);

        String start = String.valueOf(yearMonth.getYear()) +'-'+ String.valueOf(yearMonth.getMonthValue()) + "-01";
        String end = String.valueOf(yearMonth.getYear()) + '-'+ String.valueOf(yearMonth.getMonthValue()+1) + "-01";
        List<Integer> collect = diaryDao.getAllDays(allDay.getUser_id(),start,end).stream().map(date ->
                date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfMonth()
        ).collect(Collectors.toList());
        return collect;
    }

    public StatDto getStat(User user) {
        StatDto statDto = new StatDto();
//      获取连续天数
        LocalDate now = LocalDate.now();
        LocalDate yestoday = now.minusDays(1);
        List<Diary> todaydiary = diaryDao.getDiary(user.getId(), now);
        List<Diary> yestodaydiary = diaryDao.getDiary(user.getId(), yestoday);

        if (todaydiary.isEmpty()) {
            Long days = yestodaydiary.isEmpty() ? 0L : yestodaydiary.get(0).getDays();
            statDto.setDays(days);
        } else {
            statDto.setDays(todaydiary.get(0).getDays());
        }

//       获取类型和数量
        List<Stat> statList = diaryDao.getStat(user.getId());
        int total = 0;
        for (Stat stat : statList) {
            total = total + stat.getCount();
        }

        statDto.setStats(statList);
        statDto.setTotalCount(total);

        return statDto;
    }
}
