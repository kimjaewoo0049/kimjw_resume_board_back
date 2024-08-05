package com.example.kimjw_board.services;

import com.example.kimjw_board.authentication.ClientMemberLoader;
import com.example.kimjw_board.dtos.ContentDto;
import com.example.kimjw_board.mappers.ContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {

    @Autowired
    ContentMapper contentMapper;
    @Autowired
    ClientMemberLoader clientMemberLoader;

    public int postCount(){
        return contentMapper.postCount();
    }
    public List<ContentDto> boardList(ContentDto contentDto){
        contentDto.setCurrentPage(contentDto.getCurrentPage()*contentDto.getPostNum()); // 화면에서 보내주는 현재페이지와 한페이지에 출력할 값을 곱해서 setCurrentPage 해준다.
        return contentMapper.boardList(contentDto);
    }
    public ContentDto contentView (int uid){
        return contentMapper.contentView(uid);
    }
    public String contentDelete(int uid){
        ContentDto contentDto = new ContentDto();
        contentDto.setUid(uid);
        contentDto.setUserUid(clientMemberLoader.userInfo());
        ContentDto searchContent = contentMapper.userContentFind(contentDto);

        if(searchContent != null){
            contentMapper.contentDelete(contentDto);
            return "Success";
        }else{
            return "fail";
        }
    }
    public ContentDto contentFind(ContentDto contentDto){
        return contentMapper.contentFind(contentDto);
    }
    public String contentUpdate(ContentDto contentDto){
        contentDto.setUserUid(clientMemberLoader.userInfo());  // jwt에 있는 userUid를 contentDto에 삽입
        ContentDto searchContent = contentMapper.userContentFind(contentDto); //

        if(searchContent != null){
            contentMapper.contentUpdate(contentDto);
            return "Success";
        }else{
            return "fail";
        }
    }
    public void contentCreate(ContentDto contentDto){
        contentDto.setUserUid(clientMemberLoader.userInfo());
        System.out.println("content-create service");
        contentMapper.contentCreate(contentDto);
    }
}