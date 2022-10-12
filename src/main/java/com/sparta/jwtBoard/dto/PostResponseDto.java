package com.sparta.jwtBoard.dto;

import com.sparta.jwtBoard.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private String title;
    private String contents;

    private Long id;
    private String author;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto (Post post){
        this.title = post.getTitle();
        this.contents = post.getContents();

        this.id = post.getId();
        this.author = post.getUser().getUsername();
        this.createAt = post.getCreateAt();
        this.modifiedAt = post.getModifiedAt();
    }


}
