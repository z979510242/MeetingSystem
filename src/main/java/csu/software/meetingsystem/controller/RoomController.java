package csu.software.meetingsystem.controller;

import csu.software.meetingsystem.entity.Log;
import csu.software.meetingsystem.entity.Room;
import csu.software.meetingsystem.exception.ApiError;
import csu.software.meetingsystem.response.CustomResponse;
import csu.software.meetingsystem.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/judge/")
    public Log judgeRoomOrder(@RequestParam Integer roomId, @RequestParam Long date, @RequestParam Integer log ){

        Room room = new Room();
        room.setId(roomId);
        Date date1 = new Date(date);
        return roomService.judgeRoomOrder(room, date1, log);
    }

    @GetMapping("/")
    public List<Room> getAllRooms(@RequestParam(required=false) Integer id, @RequestParam(required=false) String campus, @RequestParam(required=false) String storey, @RequestParam(required=false) String floor, @RequestParam(required=false) String type, @RequestParam(required=false) Date date, @RequestParam(required=false) Integer time) {
        Room room = new Room();
        //指定查询
        if (id != null){
            room.setId(id);
            List<Room> roomList = new ArrayList<>();
            Room room1 = roomService.findRoom(room);
            roomList.add(room1);
             return roomList;
            //泛查询
        } else if (campus != null && storey != null && floor != null && type != null && date!=null){
            room.setCampus(campus);
            room.setStorey(storey);
            room.setFloor(floor);
            room.setType(type);
            List<Room> roomList = roomService.selectRoomsAccurately(room);
            List<Room> rooms = new ArrayList<>();
            for (Room room1 : roomList) {
                rooms.add(room1);
            }
            return rooms;
        }
        return roomService.findAllRooms();

    }

    @GetMapping("/building/")
    public List<String> getBuilding(@RequestParam String campus){
        List<String> list = roomService.selectBuilding(campus);
        HashSet<String> set = new HashSet<String>(list.size());
        List<String> result = new ArrayList<String>(list.size());
        for (String s : list) {
            if (set.add(s))
                result.add(s);
        }
        return result;
    }
    @GetMapping("/rooms/")
    public List<Integer> getRoomsByBuilding(@RequestParam String storey){
        List<Integer> list = roomService.selectRoomsByBuilding(storey);
        HashSet<Integer> set = new HashSet<Integer>(list.size());
        List<Integer> result = new ArrayList<Integer>(list.size());
        for (Integer s : list) {
            if (set.add(s))
                result.add(s);
        }
        return result;
    }

    @PostMapping("/selectOption/")
    public List<Room> getRoomsByOptions(@RequestBody Room room){
        return roomService.selectRoomsByOptions(room);
    }

    @PostMapping("/")
    public Object createRoom(@RequestBody Room room) {
        if (roomService.findRoomAcc(room) == null){

            roomService.createRoom(room);
            return roomService.findRoom(room);
        }

        ApiError apiError = new ApiError(ZonedDateTime.now(ZoneId.of("Z")),
                "添加会议室失败！该会议室已存在，请重新填写相关信息",
                HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRoom(@PathVariable(value = "id") Integer id) {

        try {
            boolean status = roomService.deleteRoom(id);
            if (status) {
                return new ResponseEntity<>(new CustomResponse("Delete room "+id+" successful",
                        HttpStatus.OK), HttpStatus.OK);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/")
    public Room updateRoom(@RequestBody Room room) {
        roomService.updateRoom(room);
        return roomService.findRoom(room);
    }
}
