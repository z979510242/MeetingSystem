package csu.software.meetingsystem.service;


import csu.software.meetingsystem.entity.Message;
import csu.software.meetingsystem.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageMapper messageMapper;

    public List<Message> selectAllMessages(){
        return messageMapper.selectAllMessages();
    }
    public List<Message> selectMessagesByDate(Date date){
        return messageMapper.selectMessagesByDate(date);
    }
    public Message selectMessageById(int id){
        return messageMapper.selectMessageById(id);
    }
    public boolean updateMessage(Message message){
        return messageMapper.updateMessage(message);
    }
    public boolean insertMessage(Message message){
        return  messageMapper.insertMessage(message);
    }
    public boolean deleteMessage(Message message){
        System.out.println("here");
        boolean b = messageMapper.deleteMessage(message);
        System.out.println(b);
        return b;
//        return  messageMapper.deleteMessage(message);
    }

    public Integer countMessage(){
        return  messageMapper.countMessage();
    }
}
