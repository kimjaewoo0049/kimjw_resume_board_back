package com.example.kimjw_board.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ClientMemberLoader {
    public int userInfo (){
        int userInfo = (int)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userInfo;
    }
}
