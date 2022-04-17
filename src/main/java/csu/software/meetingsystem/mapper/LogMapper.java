package csu.software.meetingsystem.mapper;

import csu.software.meetingsystem.entity.Log;
//import csu.software.meetingsystem.entity.User;
import csu.software.meetingsystem.entity.Room;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Mapper
@Repository
public interface LogMapper {


    @Select("select * from log order by date desc, log desc" )
    List<Log> selectAllLogs();

    @Select("select * from log where userId = #{id} order by date desc, log desc")
     List<Log> selectLogsByUserId(int id);

    @Select("select * from log where userId = #{userId} and date = #{date} order by log desc")
    List<Log> selectLogsByUserIdAndDate(Log log);

    @Select("select * from log where date = #{date}")
     List<Log> selectLogsByDate(Date date);

    @Select("select * from log where roomId = #{roomId}")
     List<Log> selectLogsByRoomId(Log log);


    @Select("select * from log where userId = #{userId} and log = #{log} order by date desc ")
    List<Log> selectLogsByUserIdAndLog(Log log);

    @Select("select * from log where roomId = #{roomId} and date =#{date}")
    List<Log> selectLogsByRoomIdAndDate(Log log);


    @Select("select * from log where id = #{id}")
     Log selectLog(int id);


    @Insert("insert into log (id,userid,date,roomId,log) values (#{id},#{userId},#{date},#{roomId},#{log})")
     boolean insertLog(Log log);

    @Delete("delete from log where id = #{id}")
     boolean deleteLog(Log log);

    @Update("update Log set userId=#{userId},date=#{date},roomId=#{roomId},log=#{log} where id=#{id}")
     boolean updateLog(Log log);

    @Select("select * from log where date =#{date} and roomId = #{roomId} and log = #{log}")
    Log judgeRoomOrder(Log log);

    @Select("select count(*) from log where userId =#{userId}")
    Integer countRoomsById(Integer userId);

    @Delete("DELETE FROM log")
    boolean clearLogs();

//    List<Log> selectLogByDateAndRoomId(Log log);
}
