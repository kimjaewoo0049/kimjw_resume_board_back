package com.example.kimjw_board.mappers;

import com.example.kimjw_board.dtos.UserCheckDto;
import com.example.kimjw_board.dtos.UserDto;
import com.example.kimjw_board.models.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper{
    List<UserDto> userList(UserDto userDto);
    UserCheckDto userCheck(UserCheckDto userCheckDto);
    Optional<User> login(String userId); // 스프링 시큐리티 구현중
    void join (User user); // 스프링 시큐리티 구현완료 회원가입기능
}
