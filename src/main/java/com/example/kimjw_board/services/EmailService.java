package com.example.kimjw_board.services;


import com.example.kimjw_board.dtos.EmailDto;
import com.example.kimjw_board.mappers.EmailMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class EmailService {
    Map<String, String> verificationCodes = new ConcurrentHashMap<>();
    private final JavaMailSender javaMailSender;  //메일 보내주는놈
    private final EmailMapper emailMapper;
    private final PasswordEncoder passwordEncoder;  //패스워드 수정시 암호화 해주는놈

    public EmailService(JavaMailSender javaMailSender, EmailMapper emailMapper, PasswordEncoder passwordEncoder) {
        this.javaMailSender = javaMailSender;
        this.emailMapper = emailMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Async
    public boolean sendMail(EmailDto emailDto){  // 이메일 보내기
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage(); // 보낼 메일 객체화

// 6자리 랜덤 수 만들기
        int certificationNum = ThreadLocalRandom.current().nextInt(100000, 1000000);
// 만단 cerificationCodes는 in-memory에 넣는법으로 hip메모리에 저장되고 put을 통해 넣는다.
        verificationCodes.put("certification", Integer.toString(certificationNum));

        // 메일을 set을 통해 설정
        // to => 받을사람
        // Subject => 제목
        // From => 보내는 사람 (처음 설정했던 내 메일주소)
        // Text => 내용
        simpleMailMessage.setTo(emailDto.getEmail());
        simpleMailMessage.setSubject("BOARD 비밀번호 재설정 메일입니다.");
        simpleMailMessage.setFrom("jaewoomailserver@gmail.com");
        simpleMailMessage.setText("인증메일 : " + verificationCodes.get("certification"));

        try {
            javaMailSender.send(simpleMailMessage); // javaMailSender.send 메서드에 메일 내용 담음
            return true; // 메일 전송이 성공한 경우 true 반환
        } catch (Exception exception) {
            exception.printStackTrace();
            return false; // 메일 전송이 실패한 경우 false 반환
        }
    }
    public boolean rePassword (EmailDto emailDto){  // 이메일 재설정
        String code = verificationCodes.get("certification");  // sendMailReject에서 넣은 인증코드를 꺼내온다. "certification"는 키값
        emailDto.setNewPassword(passwordEncoder.encode(emailDto.getNewPassword()));

        if(Objects.equals(code, emailDto.getCode())){
            emailMapper.rePassword(emailDto);
            verificationCodes.put("certification", "");
            return true;
        }else{
            return false;
        }
    }
}
