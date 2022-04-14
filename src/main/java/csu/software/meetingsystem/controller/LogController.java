package csu.software.meetingsystem.controller;


import com.sun.org.apache.xpath.internal.objects.XNull;
import csu.software.meetingsystem.entity.Log;
import csu.software.meetingsystem.entity.Room;
import csu.software.meetingsystem.entity.User;
import csu.software.meetingsystem.exception.ApiError;
import csu.software.meetingsystem.response.CustomResponse;
import csu.software.meetingsystem.service.LogService;
import csu.software.meetingsystem.service.RoomService;
import csu.software.meetingsystem.service.UserService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    LogService logService;
    @Autowired
    RoomService roomService;
    @Autowired
    UserService userService;

    @GetMapping("/")
    public List<Log> selectAllLogs() {
        return logService.selectAllLogs();
    }

    @GetMapping("/{selectByUser}")
    public List<Log> selectLogsByUser(@PathVariable(value = "selectByUser") Integer userId) {
        User user = new User();
        user.setId(userId);
        return logService.selectLogs(user);
    }

    @GetMapping("/date/{selectByDate}")
    public List<Log> selectLogsByDate(@PathVariable(value = "selectByDate") Date date) {
        return logService.selectLogs(date);
    }

    @GetMapping("/roomId/{selectByLog}")
    public List<Log> selectLogs(@PathVariable(value = "selectByLog") Integer roomId) {
        Log log1 = new Log();
        log1.setRoomId(roomId);
        return logService.selectLogs(log1);
    }
    @GetMapping("/RoomIdAndDate/")
    public List<Log> selectLogsByRoomIdAndDate(@RequestParam Integer roomId, @RequestParam Long date) {
        Log log = new Log();
        Date date1 = new Date(date);
        log.setDate(date1);
        log.setRoomId(roomId);
        return logService.selectLogsByRoomIdAndDate(log);
    }



    @DeleteMapping("/")
    public ResponseEntity deleteLogByThreeParams(@RequestParam Integer roomId, @RequestParam Long date, @RequestParam Integer log) {
        Room room = new Room();
        room.setId(roomId);
        Date date1 = new Date(date);
        Log log1 = roomService.judgeRoomOrder(room,date1,log);
        if (log1 != null){
            try {
                boolean status = logService.deleteLog(log1);
                if (status) {
//                return CustomResponse.build("Delete room "+id+" successful", HttpStatus.OK);
                    return new ResponseEntity<>(new CustomResponse("Delete log " + log1.getId() + " successful",
                            HttpStatus.OK), HttpStatus.OK);
                }
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            ApiError apiError = new ApiError(ZonedDateTime.now(ZoneId.of("Z")),
                    "当前时间未被预约！不可删除",
                    HttpStatus.BAD_REQUEST);
            return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{deleteId}")
    public ResponseEntity deleteLog(@PathVariable(value = "deleteId") Integer id) {
        Log log = new Log();
        log.setId(id);
        try {
            boolean status = logService.deleteLog(log);
            if (status) {
//                return CustomResponse.build("Delete room "+id+" successful", HttpStatus.OK);
                return new ResponseEntity<>(new CustomResponse("Delete log " + log.getId() + " successful",
                        HttpStatus.OK), HttpStatus.OK);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/")
    public Log changeUserLog(@RequestBody Log log) {
        Room room = new Room();
        room.setId(log.getRoomId());
        Log log1 = roomService.judgeRoomOrder(room,log.getDate(),log.getLog());
        log.setId(log1.getId());


        Room tempRoom = roomService.findRoom(room);
        List<Room> roomList = roomService.selectRoomsByOptions(tempRoom);
        int size = roomList.size();
        int number = -1;
        if (size !=0){
            for (int i = 0; i < size; i++) {
                if (roomList.get(i).getId() != room.getId() && roomService.judgeRoomOrder(roomList.get(i),log.getDate(),log.getLog()) ==null)
                    number = i;
            }
            if (number != -1){
                log1.setRoomId(roomList.get(number).getId());
                Log log2 = new Log();
                log2.setRoomId(log1.getRoomId());
                log2.setUserId(log1.getUserId());
                log2.setDate(log1.getDate());
                log2.setLog(log1.getLog());
                logService.insertLog(log2);
                System.out.println("成功预约");
            }else {
                System.out.println("无法自动预约其他教室");
            }
        }else {
            System.out.println("无法自动预约其他教室");
        }


        logService.updateLog(log);
        return logService.selectLog(log.getId());
    }

    @PostMapping("/insert/")
    public Object insertLog(@RequestBody Log log) {

        int times = logService.countTimesByUserIdAndLog(log);
        if (times <=6){
            Room room = new Room();
            System.out.println(log.getDate());
            room.setId(log.getRoomId());
            Log log1 = roomService.judgeRoomOrder(room, log.getDate(), log.getLog());
            if (log1 == null) {
                logService.insertLog(log);
                log1 = roomService.judgeRoomOrder(room, log.getDate(), log.getLog());
                return log1;
            }
            ApiError apiError = new ApiError(ZonedDateTime.now(ZoneId.of("Z")),
                    "当前时间已被预约！请重新选择",
                    HttpStatus.BAD_REQUEST);
            return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);
        }else {
            ApiError apiError = new ApiError(ZonedDateTime.now(ZoneId.of("Z")),
                    "您近60天以来预约该房间当前时段次数较多，请预约该时段其他房间!",
                    HttpStatus.BAD_REQUEST);
            return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);
        }




    }

    @GetMapping("/count/{id}")
    public Integer countRoomsById(@PathVariable(value = "id") Integer id) {
        return logService.countRoomsById(id);
    }

    @PostMapping("/updateUser/")
    public Log updateLogUser(@RequestBody Log log){
        Room room = new Room();
        room.setId(log.getRoomId());
        Log log1 = roomService.judgeRoomOrder(room, log.getDate(), log.getLog());
        log.setId(log1.getId());
        User user = new User();
        user.setId(log1.getUserId());
        User orderUser = userService.selectUser(user);
        //tel 通知
        System.out.println(orderUser.getId());
        System.out.println(orderUser.getName());
        System.out.println(orderUser.getTel());

        logService.updateLog(log);
        return logService.selectLog(log.getId());
    }

//    @DeleteMapping("/")
//    public List<Log> clearLogs(){
//        logService.clearLogs();
//        return logService.selectAllLogs();
//    }
}
