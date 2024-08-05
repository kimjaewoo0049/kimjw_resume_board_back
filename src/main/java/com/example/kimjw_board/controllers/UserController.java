package com.example.kimjw_board.controllers;

import com.example.kimjw_board.dtos.UserCheckDto;
import com.example.kimjw_board.dtos.UserDto;
import com.example.kimjw_board.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/userlist")
    public List<UserDto> userList (@RequestBody UserDto userDto){
        List<UserDto> test = userService.userList(userDto);
        System.out.println(test);
        return test;
    }

    @PostMapping("/user-check")
    public int userCheck (@RequestBody UserCheckDto userCheckDto){
        int checkValue = userService.userCheck(userCheckDto);
        return checkValue;
    }
    @PostMapping("/findId")
    public String findId (@RequestBody UserDto userDto){
        return userService.findId(userDto);
    }
    @PostMapping("/pwCheck")
    public boolean pwCheck(@RequestBody UserDto userDto){
        return userService.pwCheck(userDto);
    }

    @PostMapping("/infoRegex")
    public String infoRegex (@RequestBody UserDto userDto){
        return userService.infoRegex(userDto);
    }

    @PostMapping("/join")
    public ResponseEntity<String> join (@RequestBody UserDto userDto){
        userService.join(userDto);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/test")
    public String getTest(){
        return "Hello Get";
    }

    @PostMapping("/test")
    public String postTest(){
        // Authentication안에 있는 userUid 가지고 오기 SecurityContextHolder를 까서 내용 가지고 오기
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "Hello Post";
    }

}
