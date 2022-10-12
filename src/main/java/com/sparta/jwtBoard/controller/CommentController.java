package com.sparta.jwtBoard.controller;

import com.sparta.jwtBoard.dto.CommentRequestDto;
import com.sparta.jwtBoard.dto.CommentResponseDto;
import com.sparta.jwtBoard.security.UserDetailImp;
import com.sparta.jwtBoard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    //댓글 쓰기
    @PostMapping("/api/auth/comment")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailImp userDetailImp) {
        return commentService.createComment(commentRequestDto, userDetailImp.getUsername());
    }

    //댓글 수정
    @PostMapping("/api/auth/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailImp userDetailImp) throws IllegalAccessException {
        return commentService.updateComment(id, commentRequestDto, userDetailImp.getUsername());
    }

    //댓글 삭제
    @DeleteMapping("/api/auth/comment/{id}")
    public String deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailImp userDetailImp) {
        return commentService.deleteComment(id, userDetailImp.getUsername());
    }


    //댓글 전체목록 보기
    @GetMapping("/api/comment/{id}")
    public List<CommentResponseDto> getCommentAllOfPost(@PathVariable Long id) {
        return commentService.getCommentAllOfPost(id);
    }
}
