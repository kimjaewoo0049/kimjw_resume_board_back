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
    public String findId (UserDto userDto){
        String userId = "";
        UserDto userInfo = userMapper.findId(userDto);
        if(userInfo != null){
            userId = userInfo.getUserId();
        }
        return userId;
    }
    public boolean pwCheck (UserDto userDto){
        boolean pwCheck = userMapper.pwCheck(userDto)==1?true:false;
        return pwCheck;
    }
    public String infoRegex(UserDto userDto){
        String regexMsg = "성공";
        if(userMapper.idRegex(userDto) == true){
            regexMsg = "이미 등록된 ID 입니다. 다른 ID를 입력해주세요";
        } else if (userMapper.nickNameRegex(userDto) == true){
            regexMsg = "이미 등록된 닉네임 입니다. 다른 닉네임을 입력해주세요";
        } else if (userMapper.emailRegex(userDto) == true) {
            regexMsg = "이미 등록된 이메일 입니다. 다른 메일주소를 입력해주세요";
        }
        return regexMsg;
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
