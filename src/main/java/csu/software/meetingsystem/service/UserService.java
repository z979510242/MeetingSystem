package csu.software.meetingsystem.service;


import csu.software.meetingsystem.entity.User;
import csu.software.meetingsystem.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;


    public boolean insertUser(User user){
        return userMapper.insertUser(user);
    }

    public User login(User user){
        return userMapper.loginUser(user);
    }
    public boolean deleteUser(User user){
        return userMapper.deleteUser(user);
    }
    public boolean updateUser(User user){
        return userMapper.updateUser(user);
    }
    public List<User> selectAllUser(){
        return userMapper.selectALlUser();
    }
    public User selectUser(User user){
        return userMapper.selectUser(user);
    }
}
