package com.sparta.jwtBoard.dto;

import com.sparta.jwtBoard.entity.User;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long postId;
    private String comment;
    private User user;
}
