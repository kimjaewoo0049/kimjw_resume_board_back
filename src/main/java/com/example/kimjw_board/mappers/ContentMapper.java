package com.example.kimjw_board.mappers;

import com.example.kimjw_board.dtos.ContentDto;
import com.example.kimjw_board.dtos.UserCheckDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContentMapper {
     int postCount();
     List<ContentDto> boardList(ContentDto contentDto);
     ContentDto contentView (ContentDto contentDto);
     void contentDelete (ContentDto contentDto);
     ContentDto contentFind(ContentDto contentDto);
    ContentDto userContentFind(ContentDto contentDto);  // contentDelete와 contentUpdate에서 기능실행전 작성자인지 확인하기 위한 기능
     void contentUpdate(ContentDto contentDto);
     void contentCreate(ContentDto contentDto);
}
