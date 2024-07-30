package com.example.kimjw_board.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int uid;
    private String userId;
    private String userPw;
    private String userRating; // 유저 레벨 / 이걸로 인증함
    private String nickName;
    private String userName;
    private String address;
    private String phone;
    private String email;
    private LocalDateTime currentAt;
}
