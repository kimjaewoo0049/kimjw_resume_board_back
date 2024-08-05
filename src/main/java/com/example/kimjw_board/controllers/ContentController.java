package com.example.kimjw_board.controllers;

import com.example.kimjw_board.dtos.ContentDto;
import com.example.kimjw_board.services.ContentService;
import org.apache.tomcat.util.descriptor.web.ContextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class ContentController {

    @Autowired
    ContentService contentService;
    @PostMapping("/post-count")
    public int postCount (){
        return contentService.postCount();
    }
    @PostMapping("/board-list")  //GET
    public List<ContentDto> boardList (@RequestBody ContentDto contentDto){
        List<ContentDto> boardList = contentService.boardList(contentDto);
        return boardList;
    }
    @GetMapping("/content-view")
    public ContentDto contentView(@RequestParam(value = "uid") int uid){
        System.out.printf("테스트입니다.");
        return contentService.contentView(uid);
    }
    @PostMapping("/content-find")  //GET
    public ContentDto contentFind (@RequestBody ContentDto contentDto){
        return contentService.contentFind(contentDto);
    }
    @DeleteMapping("/content-delete")
    public String contentDelete(@RequestParam(value = "uid") int uid){
        return contentService.contentDelete(uid);
    }
    @PutMapping("/content-update")
    public String contentUpdate(@RequestBody ContentDto contentDto){
        return contentService.contentUpdate(contentDto);
    }
    @PostMapping("/content-create")
    public ResponseEntity<String> contentCreate(@RequestBody ContentDto contentDto){
        contentService.contentCreate(contentDto);
        return ResponseEntity.ok("Success");
    }
}
