package csu.software.meetingsystem.service;

import csu.software.meetingsystem.entity.Meeting;
import csu.software.meetingsystem.mapper.MeetingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingService {
    @Autowired
    MeetingMapper meetingMapper;

    public boolean insertMeeting(Meeting meeting){
        return meetingMapper.insertMeeting(meeting);
    }
    public boolean updateMeeting(Meeting meeting){
        return meetingMapper.updateMeeting(meeting);
    }
    public boolean deleteMeeting(Meeting meeting){
        return meetingMapper.deleteMeeting(meeting);
    }
    public List<Meeting> selectAllMeetings(){
        return meetingMapper.selectAllMeetings();
    }
    public Meeting selectMeeting(Meeting meeting){
        return meetingMapper.selectMeeting(meeting.getId());
    }
    public List<Meeting> selectMeetingByWeek(Meeting meeting){
        return meetingMapper.selectMeetingsByWeek(meeting);
    }
}
