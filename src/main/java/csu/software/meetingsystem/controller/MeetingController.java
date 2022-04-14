package csu.software.meetingsystem.controller;

import csu.software.meetingsystem.entity.Log;
import csu.software.meetingsystem.entity.Meeting;
import csu.software.meetingsystem.entity.Room;
import csu.software.meetingsystem.response.CustomResponse;
import csu.software.meetingsystem.service.LogService;
import csu.software.meetingsystem.service.MeetingService;
import csu.software.meetingsystem.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/meeting")
public class MeetingController {
    @Autowired
    MeetingService meetingService;

    @Autowired
    RoomService roomService;

    @Autowired
    LogService logService;

    @GetMapping("/")
    public List<Meeting> selectAllMeetings(){
        return meetingService.selectAllMeetings();
    }
    @PostMapping("/week/{week}")
    public List<Meeting> selectMeetingByWeek(@PathVariable(value = "week")  int week){
        Meeting meeting = new Meeting();
        meeting.setWeek(week);
        return meetingService.selectMeetingByWeek(meeting);
    }
    @PostMapping("/id/{id}")
    public Meeting selectMeeting(@PathVariable(value = "id")  int id){
        Meeting meeting = new Meeting();
        meeting.setId(id);
        return meetingService.selectMeeting(meeting);
    }
    @PatchMapping("/")
    public Meeting updateMeeting(@RequestBody Meeting meeting,@RequestParam Integer time){
        Meeting status = meetingService.selectMeeting(meeting);


        Meeting meeting1 = new Meeting();
        meeting1.setId(status.getId());
        meeting1.setWeek(status.getWeek());
        meeting1.setWeekday(status.getWeekday());
        meeting1.setDate(status.getDate());
        meeting1.setRoomId(0);
        int roomId = status.getRoomId();
        Room room = new Room();
        room.setId(roomId);
        Room room1 = roomService.findRoom(room);
        //然后查询相同配置、相同时间的教室
        List<Room> rooms = roomService.selectRoomsByOptions(room1);
        if (rooms.size() ==0){
            System.out.println("继续预约失败");
        }else {
            for (int i = 0; i < rooms.size(); i++) {
                Meeting m = new Meeting();
                m.setId(rooms.get(i).getId());
                Meeting meeting2 = meetingService.selectMeeting(m);
                if (meeting2!=null){
                    switch (time){
                        case 8:{
                            if (meeting2.getEight()==null)
                                meeting1.setRoomId(meeting2.getRoomId());
                            break;
                        }
                        case 10:{
                            if (meeting2.getTen()==null)
                                meeting1.setRoomId(meeting2.getRoomId());
                            break;

                        }
                        case 12:{
                            if (meeting2.getTwelve()==null)
                                meeting1.setRoomId(meeting2.getRoomId());
                            break;

                        }
                        case 14:{
                            if (meeting2.getFourteen()==null)
                                meeting1.setRoomId(meeting2.getRoomId());
                            break;

                        }
                        case 16:{
                            if (meeting2.getSixteen()==null)
                                meeting1.setRoomId(meeting2.getRoomId());
                            break;

                        }
                        case 18:{
                            if (meeting2.getEighteen()==null)
                                meeting1.setRoomId(meeting2.getRoomId());
                            break;

                        }
                        case 20:{
                            if (meeting2.getTwenty()==null)
                                meeting1.setRoomId(meeting2.getRoomId());
                            break;

                        }
                        case 22:{
                            if (meeting2.getTwentyTwo()==null)
                                meeting1.setRoomId(meeting2.getRoomId());
                            break;

                        }
                        default:break;
                    }
                }else {
                    meeting1.setRoomId(rooms.get(i).getId());
                    break;
                }
            }
        }


        String userLog;
        switch (time) {
            case 8:{
                userLog=status.getEight();
                meeting.setEight(meeting.getEight()+","+userLog);

                meeting1.setEight(userLog);
            }
            case 10:{
                userLog=status.getTen();
                meeting.setEight(meeting.getTen()+","+userLog);
                meeting1.setTen(userLog);
            }
            case 12:{
                userLog=status.getTwelve();
                meeting.setEight(meeting.getTwelve()+","+userLog);
                meeting1.setTwelve(userLog);
            }
            case 14:{
                userLog=status.getFourteen();
                meeting.setEight(meeting.getFourteen()+","+userLog);
                meeting1.setFourteen(userLog);
            }
            case 16:{
                userLog=status.getSixteen();
                meeting.setEight(meeting.getSixteen()+","+userLog);
                meeting1.setSixteen(userLog);
            }
            case 18:{
                userLog=status.getEighteen();
                meeting.setEight(meeting.getEighteen()+","+userLog);
                meeting1.setEighteen(userLog);
            }
            case 20:{
                userLog=status.getTwenty();
                meeting.setEight(meeting.getTwenty()+","+userLog);
                meeting1.setTwenty(userLog);
            }
            case 22:{
                userLog=status.getTwentyTwo();
                meeting.setEight(meeting.getTwentyTwo()+","+userLog);
                meeting1.setTwentyTwo(userLog);
            }
        }

        meetingService.updateMeeting(meeting);
        //在预约下一个房间
        if (meeting1.getRoomId()!=0){
            if (meetingService.selectMeeting(meeting1) !=null){
                meetingService.updateMeeting(meeting1);
            }else
                meetingService.insertMeeting(meeting1);
        }



        return meetingService.selectMeeting(meeting);
    }
    @PostMapping("/{insert}")
    public boolean insertMeeting(@PathVariable(value = "insert") Meeting meeting ,@RequestParam Integer userid,@RequestParam Integer time){
        //先获取今日日期
        Log log = new Log();
        log.setUserId(userid);
        log.setDate(meeting.getDate());
        log.setRoomId(meeting.getRoomId());
        log.setLog(time);
//        Date date = new Date(System.currentTimeMillis());
//        meeting.setDate(date);
//        System.out.println(date);
        logService.insertLog(log);
        return meetingService.insertMeeting(meeting);
    }
    @DeleteMapping("/{deleteId}")
    public ResponseEntity deleteMeeting(@PathVariable(value = "deleteId") Integer id) {
        Meeting meeting = new Meeting();
        meeting.setId(id);
        try {
            boolean status = meetingService.deleteMeeting(meeting);
            if (status) {
//                return CustomResponse.build("Delete room "+id+" successful", HttpStatus.OK);
                return new ResponseEntity<>(new CustomResponse("Delete meetingOrder "+id+" successful",
                        HttpStatus.OK), HttpStatus.OK);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
