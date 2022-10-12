package com.sparta.jwtBoard.controller;

import com.sparta.jwtBoard.dto.PostListResponseDto;
import com.sparta.jwtBoard.dto.PostRequestDto;
import com.sparta.jwtBoard.dto.PostResponseDto;
import com.sparta.jwtBoard.security.UserDetailImp;
import com.sparta.jwtBoard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 글 작성
    @PostMapping("/api/auth/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailImp userDetailImp) {
        return postService.createPost(postRequestDto, userDetailImp.getUsername());
    }

    // 글 전체보기
    @GetMapping("/api/posts")
    public List<PostListResponseDto> getPostAll() {
        return postService.getPostAll();
    }

    // 글 상세보기
    @GetMapping("/api/posts/{id}")
    public PostResponseDto getPostDetail(@PathVariable Long id) {
        return postService.getPostDetail(id);
    }

    // 글 수정
    @PutMapping("/api/auth/posts/{id}")
    public PostResponseDto updataPost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailImp userDetailImp) {
        return postService.updatePost(id, postRequestDto, userDetailImp.getUsername());
    }

    // 글 삭제
    @DeleteMapping("/api/auth/posts/{id}")
    public String deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailImp userDetailImp) {
        return postService.deletePost(id, userDetailImp.getUsername());
    }
}
