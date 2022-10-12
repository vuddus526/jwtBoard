package com.sparta.jwtBoard.service;

import com.sparta.jwtBoard.dto.PostListResponseDto;
import com.sparta.jwtBoard.dto.PostRequestDto;
import com.sparta.jwtBoard.dto.PostResponseDto;
import com.sparta.jwtBoard.entity.Comment;
import com.sparta.jwtBoard.entity.Post;
import com.sparta.jwtBoard.entity.User;
import com.sparta.jwtBoard.repository.CommentRepository;
import com.sparta.jwtBoard.repository.PostRepository;
import com.sparta.jwtBoard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.Temporal;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // username을 이용해서 User 객체 만들기 및 유저정보 확인 ( Response 사용해서)
//    private UserResponseDto getUser(String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow( () -> new UsernameNotFoundException("유저를 찾을 수 없습니다"));
//        UserResponseDto responseDto = new UserResponseDto(user);
//        return responseDto;
//    }

    // username을 이용해서 User 객체 만들기 및 유저정보 확인 ( user 사용해서)
    private User getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException("유저를 찾을 수 없습니다"));
        return user;
    }

    // 글작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, String username) {
        User user = getUser(username);

        Post post = new Post(postRequestDto, user);
        postRepository.save(post);

        return new PostResponseDto(post);
    }

    // 글 전체보기
    public List<PostListResponseDto> getPostAll() {
        List<Post> list = postRepository.findAllByOrderByModifiedAtDesc();

        List<PostListResponseDto> plist = new ArrayList<>();

        for(Post post : list) {
            PostListResponseDto postListResponseDto = PostListResponseDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .author(post.getUser().getUsername())
                    .createAt(post.getCreateAt())
                    .modifiedAt(post.getModifiedAt())
                    .build();
            plist.add(postListResponseDto);
        }
        return plist;
    }

    // 글 상세보기
    public PostResponseDto getPostDetail(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("해당 글이 존재하지 않습니다"));
                return new PostResponseDto(post);
    }

    // 글 수정
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, String username) {
        User user = getUser(username);
        Post post = postRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException ("해당 글이 존재하지 않습니다"));

        if (!user.getUsername().equals(post.getUser().getUsername())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다");
        }

        post.update(postRequestDto);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    // 글 삭제
    @Transactional
    public String deletePost(Long id, String username) {
        User user = getUser(username);
        Post post = postRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException ("해당 글이 존재하지 않습니다"));
        if (!user.getUsername().equals(post.getUser().getUsername())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다");
        }
        postRepository.deleteById(id);
        List<Comment> list = commentRepository.findAllByPostId(id);
        for(Comment comment : list) {
            commentRepository.deleteById(comment.getId());
        }
        return "글이 삭제되었습니다";
    }
}
