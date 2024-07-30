package com.example.kimjw_board.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserCheckDto {
    private int uid;
    private String userPw;
}
