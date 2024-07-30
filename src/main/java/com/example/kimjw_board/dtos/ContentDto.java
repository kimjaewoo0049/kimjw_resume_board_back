package com.example.kimjw_board.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ContentDto {
    private int uid;
    private int userUid;
    private String userName;
    private String title;
    private String content;
    private Date createAt;
    private int currentPage; // 현재 페이지
    private int postNum; // 게시글을 몇개씩 출력할 것인지
    private int postCount; // 전체 게시글수
}
