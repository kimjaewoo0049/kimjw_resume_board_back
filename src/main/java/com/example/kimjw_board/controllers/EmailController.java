package com.example.kimjw_board.controllers;

import com.example.kimjw_board.dtos.EmailDto;
import com.example.kimjw_board.services.EmailService;
import com.example.kimjw_board.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    EmailService emailService;

    public EmailController(UserService userService, EmailService emailService) {
        this.emailService = emailService;
    }
    @PostMapping("/sendMail")
    public boolean sendMail(@RequestBody EmailDto emailDto){
        return emailService.sendMail(emailDto);
    }
    @PostMapping("/rePassword")    // 이메일 재설정
    public boolean rePassword(@RequestBody EmailDto emailDto){
        System.out.printf("test" + emailDto.getUserId() + emailDto.getEmail() + emailDto.getCode() + emailDto.getNewPassword());
        return emailService.rePassword(emailDto);
    }
}
