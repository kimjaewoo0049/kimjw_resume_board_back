package com.example.kimjw_board.mappers;

import com.example.kimjw_board.dtos.EmailDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailMapper {
    void rePassword(EmailDto emailDto);
}
