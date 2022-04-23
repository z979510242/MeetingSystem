package csu.software.meetingsystem.controller;


import csu.software.meetingsystem.entity.Message;
import csu.software.meetingsystem.entity.User;
import csu.software.meetingsystem.response.CustomResponse;
import csu.software.meetingsystem.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

//    @GetMapping("/")
//    public List<Message> selectAllMessages(){
//        return messageService.selectAllMessages();
//    }
    @GetMapping("/")
    public List<Message> selectMessages(@RequestParam(required=false) Long date, @RequestParam(required=false) Integer id){

        if (date!=null && id == null){
            Date date1 = new Date(date);
            return messageService.selectMessagesByDate(date1);
        }else if (date ==null && id!=null){
            Message message = messageService.selectMessageById(id);
            List<Message> messages = new ArrayList<>();
            messages.add(message);
            return messages;
        }
        return messageService.selectAllMessages();
    }
//    @PostMapping("/{selectMessageById}")
//    public Message selectMessageById(@PathVariable(value = "selectMessageById")int id){
//        return messageService.selectMessageById(id);
//    }
    @PatchMapping("/update/")
    public boolean updateMessage(@RequestBody Message message){
        Message m = messageService.selectMessageById(message.getId());
        m.setMessage(message.getMessage() +" <br>上一次编辑时间："+m.getDate());
        m.setDate(message.getDate());
        m.setTitle(message.getTitle());
        return messageService.updateMessage(m);
    }
    @PostMapping("/insert/")
    public boolean insertMessage(@RequestBody Message message){
        return  messageService.insertMessage(message);
    }

//    @DeleteMapping("/")
//    public ResponseEntity deleteMessage (@RequestBody Message message) {
    @DeleteMapping("/{deleteMessage}")
    public ResponseEntity deleteMessage (@PathVariable(value = "deleteMessage") Integer id) {

        try {
            Message message = new Message();
            message.setId(id);

            boolean status = messageService.deleteMessage(message);


            if (status) {
                return new ResponseEntity<>(new CustomResponse("Delete message "+id+" successful",
                        HttpStatus.OK), HttpStatus.OK);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/count/")
    public Integer countMessage(){
        return messageService.countMessage();
    }

}
