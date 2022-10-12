package com.sparta.jwtBoard.dto;

import com.sparta.jwtBoard.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String author;  // username
    private String comment;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto (Comment comment) {
        this.id = comment.getId();
        this.author = comment.getUser().getUsername();
        this.comment = comment.getComment();
        this.createAt = comment.getCreateAt();
        this.modifiedAt = comment.getModifiedAt();
    }
        // 생성메서드
//    public static CommentResponseDto createeCommentResponseDto(Comment coment) {
//        return new CommentResponseDto(coment);
//    }
}
