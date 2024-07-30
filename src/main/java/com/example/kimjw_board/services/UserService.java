package com.example.kimjw_board.services;

import com.example.kimjw_board.dtos.UserCheckDto;
import com.example.kimjw_board.dtos.UserDto;
import com.example.kimjw_board.mappers.UserMapper;
import com.example.kimjw_board.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(PasswordEncoder passwordEncoder, UserMapper userMapper){
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public List<UserDto> userList (UserDto userDto){
        System.out.println("userUid_" + userDto.getUid());
        return userMapper.userList(userDto);
    }

    public int userCheck (UserCheckDto userCheckDto){
        UserCheckDto userData = userMapper.userCheck(userCheckDto);
        if( userData != null){
            return 1;
        }else {
            return 0;
        }
    }
    public void join (UserDto userDto){
        
        User user = User.builder()
                .userId(userDto.getUserId())
                .userPw(passwordEncoder.encode(userDto.getUserPw()))
                .userName(userDto.getUserName())
                .nickName(userDto.getNickName())
                .address(userDto.getAddress())
                .phone(userDto.getPhone())
                .email(userDto.getEmail())
                .build();
        System.out.println(user + "user 객체");
        userMapper.join(user);
    }
}
