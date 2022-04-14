package csu.software.meetingsystem.mapper;


import csu.software.meetingsystem.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import javax.websocket.server.ServerEndpoint;
import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    @Update("update USER set name =#{name },password=#{password},post=#{post},tel=#{tel},priority=#{priority}, email=#{email} where id=#{id}")
    public boolean updateUser(User user);

    @Select("select * from USER")
    public List<User> selectALlUser();

    @Select("select * from USER where id =#{id}")
    public  User  selectUser(User user);

    @Select("select * from USER where id=#{id} and password = #{password}")
    public User loginUser(User user);

    @Delete("delete from USER where id=#{id}")
    public boolean deleteUser(User user);

    @Insert("insert into USER (id,name ,password,email,post,tel,priority) values (#{id},#{name},#{password},#{email},#{post},#{tel},#{priority})")
    public boolean insertUser(User user);


}
