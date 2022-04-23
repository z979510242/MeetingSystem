package csu.software.meetingsystem.controller;

import csu.software.meetingsystem.entity.Room;
import csu.software.meetingsystem.entity.User;
import csu.software.meetingsystem.exception.ApiError;
import csu.software.meetingsystem.jwt.JwtUtils;
import csu.software.meetingsystem.jwt.PassToken;
import csu.software.meetingsystem.response.CustomResponse;
import csu.software.meetingsystem.response.LoginResponse;
import csu.software.meetingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.selectAllUser();
    }

    @GetMapping("/me")
    public User getCurrentUser(HttpServletRequest req) {
        String id = (String) req.getAttribute("id");

        return getUser(Integer.parseInt(id));
    }

    @PassToken
    @PostMapping("/login")
    public Object login(@RequestBody User user){

        User loginUser = userService.login(user);
        if (loginUser == null) {
            CustomResponse customResponse = new CustomResponse();
            customResponse.setMessage("Login failed");
            customResponse.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
        }
        String jwtToken = JwtUtils.createToken(Integer.toString(loginUser.getId()),loginUser.getName());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setHttpStatus(HttpStatus.OK);
        loginResponse.setMessage("Login success");

        return loginResponse;
    }
    @GetMapping("/{getId}")
    public User getUser(@PathVariable(value = "getId") Integer id){
        User user = new User();
        user.setId(id);
        User user1= userService.selectUser(user);
        user1.setPassword(null);
        return user1;
    }

    @PostMapping("/")
    public Object insertUser(@RequestBody User user){
        ApiError apiError = new ApiError(ZonedDateTime.now(ZoneId.of("Z")),
                "当前用户已经存在！请重新选择填写",
                HttpStatus.BAD_REQUEST);
        ApiError apiError2 = new ApiError(ZonedDateTime.now(ZoneId.of("Z")),
                "赋予权限失败！请重新选择填写",
                HttpStatus.BAD_REQUEST);
        if ( userService.selectUser(user) == null){
            if (user.getPriority() == 3)
                user.setPost("admin");
            else if (user.getPriority() == 2)
                user.setPost("user");
            else
                return new ResponseEntity<Object>(apiError2, HttpStatus.BAD_REQUEST);
            userService.insertUser(user);
            return userService.selectUser(user);
        }

        return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("/{deleteId}")
    public ResponseEntity deleteUser(@PathVariable(value = "deleteId") Integer id) {
        try {
            User user = new User();
            user.setId(id);
            boolean status = userService.deleteUser(user);
            if (status) {
                return new ResponseEntity<>(new CustomResponse("Delete user "+id+" successful",
                        HttpStatus.OK), HttpStatus.OK);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/")
    public Object updateUser(@RequestBody User user){

        ApiError apiError = new ApiError(ZonedDateTime.now(ZoneId.of("Z")),
                "赋予权限失败！请重新选择填写",
                HttpStatus.BAD_REQUEST);

        User user1 = null;
        if (user.getPassword() == null || user.getPassword().equals("")){
            user1=userService.selectUser(user);
            user.setPassword(user1.getPassword());

        }
        if(user.getPost().equals("admin")){
            user.setPriority(3);
        }else if (user.getPost().equals("user")){
            user.setPriority(2);
        }else {
            return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);
        }
        userService.updateUser(user);
        return userService.selectUser(user);
    }

}
