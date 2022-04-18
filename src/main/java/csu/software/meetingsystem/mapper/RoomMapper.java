package csu.software.meetingsystem.mapper;

import csu.software.meetingsystem.entity.Room;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoomMapper {

    @Insert("insert into room(room, storey, floor, campus, type, capacity , record) " +
            "values (#{room}, #{storey}, #{floor}, #{campus}, #{type}, #{capacity} , #{record})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertRoom(Room room);

    @Select("select * from room")
    List<Room> selectRooms();

    @Select("select * from room where id = #{id}")
    Room selectRoom(Room room);

    @Select("select * from room where room = #{room} and storey=#{storey}  and campus=#{campus} ")
    Room selectRoomAcc(Room room);

    @Delete("delete from room where id=#{id}")
    boolean deleteRoom(Integer id);

    @Update("update room set room = #{room}, storey=#{storey},floor=#{floor}, campus=#{campus}, type =#{type},capacity=#{capacity} ,record =#{record} where id=#{id}")
    boolean updateRoom(Room room);

    @Select(("select * from room where type =#{type} and campus=#{campus}"))
    List<Room> selectRoomsByOptions(Room room);

    @Select("select * from room where type = #{type} and campus=#{campus} and storey= #{storey} and floor=#{floor}")
    List<Room> selectRoomsAccurately(Room room);

    @Select(("select storey from room where campus =#{campus}"))
    List<String> selectBuilding(String campus);

    @Select("select room from room where storey=#{storey}")
    List<Integer> selectRoomsByBuilding(String storey);


}
