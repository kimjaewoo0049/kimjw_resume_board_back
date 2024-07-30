package com.example.kimjw_board.authentication;

import com.example.kimjw_board.mappers.UserMapper;
import com.example.kimjw_board.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {

    private final UserMapper userMapper;
    public CustomUserDetailService (UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Override
    public CustomUserDetail loadUserByUsername(String userId) throws UsernameNotFoundException { // 스프링 시큐리티의 기능으로 로그인 경로는 이메서드를 탐
        Optional<User> user = userMapper.login(userId);
        return new CustomUserDetail(user.get());
    }
}
