package com.example.kimjw_board.controllers;

import com.example.kimjw_board.dtos.ContentDto;
import com.example.kimjw_board.services.ContentService;
import org.apache.tomcat.util.descriptor.web.ContextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContentController {

    @Autowired
    ContentService contentService;
    @PostMapping("/post-count")
    public int postCount (){
        return contentService.postCount();
    }
    @PostMapping("/board-list")
    public List<ContentDto> boardList (@RequestBody ContentDto contentDto){
        List<ContentDto> boardList = contentService.boardList(contentDto);
        return boardList;
    }
    @PostMapping("/content-view")
    public ContentDto contentView(@RequestBody ContentDto contentDto){
        return contentService.contentView(contentDto);
    }
    @PostMapping("/content-delete")
    public String contentDelete(@RequestBody ContentDto contentDto){
        return contentService.contentDelete(contentDto);
    }
    @PostMapping("/content-find")
    public ContentDto contentFind (@RequestBody ContentDto contentDto){
        return contentService.contentFind(contentDto);
    }
    @PostMapping("/content-update")
    public String contentUpdate(@RequestBody ContentDto contentDto){
        return contentService.contentUpdate(contentDto);
    }
    @PostMapping("/content-create")
    public ResponseEntity<String> contentCreate(@RequestBody ContentDto contentDto){
        contentService.contentCreate(contentDto);
        return ResponseEntity.ok("Success");
    }
}
