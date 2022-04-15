package csu.software.meetingsystem;

import csu.software.meetingsystem.entity.Log;
import csu.software.meetingsystem.entity.Meeting;
import csu.software.meetingsystem.entity.Room;
import csu.software.meetingsystem.service.LogService;
import csu.software.meetingsystem.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import static com.fasterxml.jackson.databind.util.ISO8601Utils.format;

@SpringBootTest
class MeetingSystemApplicationTests {

    @Autowired
    private RoomService roomService;
    @Autowired
    LogService logService;
    @Test
    void contextLoads() {

    }
    @Test
    void testDate(){
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date);
    }

    @Test
    void addMeeting() {
        Room room = new Room();
        room.setId(1);
        room.setRoom(411);
        room.setCampus("CSU");
        room.setCapacity(10);
        room.setFloor("4");
        room.setType("meeting");
        room.setStorey("electronic");
        System.out.println(roomService.createRoom(room));
    }

    @Test
    void getRooms() {
        List<Room> rooms = roomService.findAllRooms();
        System.out.println(rooms.get(0).getCampus());
    }

    @Test
    void deleteRoom() {
        System.out.println(roomService.deleteRoom(2));
    }

    @Test
    void selectLog(){
        Date date = new Date(System.currentTimeMillis());
        System.out.println(logService.selectLogs(date).get(0).getLog());
    }

    @Test
    void selectLogs(){
        java.util.Date date1 = new java.util.Date();
        Date date = new Date(date1.getTime());
        System.out.println(date);
        Date pastDate = subtractDays(date, 60);
        System.out.println(pastDate);
        System.out.println(pastDate.getTime() > date.getTime());

    }

    public static Date subtractDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -days);
        return new Date(c.getTimeInMillis());
    }

}
