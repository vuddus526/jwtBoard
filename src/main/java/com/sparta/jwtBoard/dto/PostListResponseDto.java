package com.sparta.jwtBoard.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostListResponseDto {
    private String title;

    private Long id;
    private String author;  // username
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
}
