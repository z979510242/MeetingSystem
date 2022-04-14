package csu.software.meetingsystem.mapper;

import csu.software.meetingsystem.entity.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Mapper
@Repository
public interface MessageMapper {

    @Select("select * from message order by date desc")
    public List<Message> selectAllMessages();

    @Select("select * from message where id = #{id}")
    public Message selectMessageById(int id);

    @Select("select * from message where userId = #{userId}")
    public List<Message> selectMessagesByUserId(int userId);

    @Select("select * from message where date =#{date}")
    public List<Message> selectMessagesByDate(Date date);

    @Update("update message set userid=#{userId},date=#{date},title=#{title},message=#{message} where id=#{id}")
    public boolean updateMessage(Message message);

    @Delete("delete from message where id = #{id}")
    public boolean deleteMessage(Message message);

    @Insert("insert into message (userid,date,title,message)values(#{userId},#{date},#{title},#{message})")
    public boolean insertMessage(Message message);

    @Select("select count(*) from message")
    public Integer countMessage();
}
