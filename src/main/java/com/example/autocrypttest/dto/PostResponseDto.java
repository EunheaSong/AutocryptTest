package com.example.autocrypttest.dto;

import com.example.autocrypttest.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private String title;

    private String content;

    private String nickname;

    private boolean lock;

    public PostResponseDto () {
    }

    public PostResponseDto (Post post){
        this.title = post.getTitle();
        this.content = post.getContent();
        this.nickname = post.getUser().getNickname();
        this.lock = post.isLock();
    }

}
