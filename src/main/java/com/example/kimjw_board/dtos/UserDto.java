package com.example.kimjw_board.dtos;

import lombok.*;

import java.util.Date;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int uid;
    private String userId;
    private String userPw;
    private String userRating;
    private String nickName;
    private String userName;
    private String address;
    private String phone;
    private String email;
    private Date current_at;
}
