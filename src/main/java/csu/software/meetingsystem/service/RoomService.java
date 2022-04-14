package csu.software.meetingsystem.service;

import csu.software.meetingsystem.entity.Log;
import csu.software.meetingsystem.entity.Room;
import csu.software.meetingsystem.mapper.LogMapper;
import csu.software.meetingsystem.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class RoomService {
    @Autowired
    RoomMapper roomMapper;
    @Autowired
    LogMapper logMapper;

    public int createRoom(Room room) {
        return  roomMapper.insertRoom(room);
    }

    public List<Room> findAllRooms() {
        return roomMapper.selectRooms();
    }
    public Room findRoom(Room room){
        return roomMapper.selectRoom(room);
    }
    public  Room findRoomAcc(Room room){
        return roomMapper.selectRoomAcc(room);
    }


    public boolean deleteRoom(Integer id) {
        return roomMapper.deleteRoom(id);
    }
    public boolean updateRoom(Room room){
        return roomMapper.updateRoom(room);
    }

    public List<Room> selectRoomsByOptions(Room room){
        return roomMapper.selectRoomsByOptions(room);
    }

    public List<Room> selectRoomsAccurately(Room room){
        return roomMapper.selectRoomsAccurately(room);
    }
    public List<String> selectBuilding(String campus){
        return roomMapper.selectBuilding(campus);
    }

    public List<Integer> selectRoomsByBuilding(String storey){
        return roomMapper.selectRoomsByBuilding(storey);
    }

    public Log judgeRoomOrder(Room room , Date date, Integer time){
        Log log = new Log();
        log.setRoomId(room.getId());
        log.setDate(date);
        log.setLog(time);
        return logMapper.judgeRoomOrder(log);

    }
}
