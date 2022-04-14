package csu.software.meetingsystem.service;


import csu.software.meetingsystem.entity.Log;
import csu.software.meetingsystem.entity.User;
import csu.software.meetingsystem.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class LogService {

    @Autowired
    LogMapper logMapper;

    public List<Log> selectAllLogs(){
        return logMapper.selectAllLogs();
    }


    public List<Log> selectLogs(User user){

        return logMapper.selectLogsByUserId(user.getId());
    }
    public Integer countRoomsById(Integer id){
        return logMapper.countRoomsById(id);
    }
    public List<Log> selectLogs(Date date){
        return logMapper.selectLogsByDate(date);
    }
    public List<Log> selectLogs(Log log){
        return logMapper.selectLogsByRoomId(log);
    }
    public Log selectLog(int id){
        return logMapper.selectLog(id);
    }

    public boolean deleteLog(Log log){
        return logMapper.deleteLog(log);
    }
    public boolean updateLog(Log log){
        return logMapper.updateLog(log);
    }
    public boolean insertLog(Log log){
        return logMapper.insertLog(log);
    }
    public List<Log> selectLogsByRoomIdAndDate(Log log){
        return logMapper.selectLogsByRoomIdAndDate(log);
    }

    public boolean clearLogs(){
        return logMapper.clearLogs();
    }

    public Integer countTimesByUserIdAndLog(Log log){
        List<Log> logs = logMapper.selectLogsByUserIdAndLog(log);
//        System.out.println(log.getDate().getMonth());
        String weekday = dateToWeekUtil(log.getDate().toString());
        Date pastDate = subtractDays(log.getDate(), 60);
        long seconds = pastDate.getTime();
        int times = 0;
        for (Log l : logs) {
            String s = dateToWeekUtil(l.getDate().toString());
            if (s.equals(weekday) && !l.getDate().toString().equals( log.getDate().toString())  && l.getDate().getTime() >= seconds){
                times++;
            }
        }
        return times;
    }

    public static String dateToWeekUtil(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 日历
        java.util.Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static Date subtractDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -days);
        return new Date(c.getTimeInMillis());
    }


}
