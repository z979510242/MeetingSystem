package csu.software.meetingsystem.mapper;

import csu.software.meetingsystem.entity.Log;
import csu.software.meetingsystem.entity.Meeting;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MeetingMapper {

    @Select("select * from Meeting")
    public List<Meeting> selectAllMeetings();

    @Select("select * from Meeting where week = #{week}")
    public List<Meeting> selectMeetingsByWeek(Meeting meeting);


    @Select("select * from Meeting where id = #{id}")
    public Meeting selectMeeting(int id);


    @Insert("insert into Meeting(date,week,weekday,roomId,eight,ten,twelve,fourteen,sixteen,eighteen,twenty,twentyTwo) values" +
            "#{date},#{week},#{weekday},#{roomId},#{eight},#{ten},#{twelve},#{fourteen},#{sixteen},#{eighteen},#{twenty},#{twentyTwo}")
    public boolean insertMeeting(Meeting meeting);

    @Delete("delete from meeting where id = #{id}")
    public boolean deleteMeeting(Meeting meeting);

    @Update("update Meeting set date=#{date},week=#{week},weekday=#{weekday},roomId=#{roomId}" +
            ",eight=#{eight},ten=#{ten},twelve=#{twelve},fourteen=#{fourteen},sixteen=#{sixteen},eighteen=#{eighteen},twenty=#{twenty},twentyTwo=#{twentyTwo}" +
            " where id=#{id}")
    public boolean updateMeeting(Meeting meeting);
}


