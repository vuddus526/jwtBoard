package com.sparta.jwtBoard.service;

import com.sparta.jwtBoard.dto.CommentRequestDto;
import com.sparta.jwtBoard.dto.CommentResponseDto;
import com.sparta.jwtBoard.entity.Comment;
import com.sparta.jwtBoard.entity.User;
import com.sparta.jwtBoard.repository.CommentRepository;
import com.sparta.jwtBoard.repository.PostRepository;
import com.sparta.jwtBoard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private User getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow( () -> new UsernameNotFoundException("유저를 찾을 수 없습니다"));
        return user;
    }

    public void postCheck(CommentRequestDto commentRequestDto) {
        postRepository.findById( commentRequestDto.getPostId() )
                .orElseThrow( () -> new IllegalArgumentException("해당 글이 존재하지 않습니다"));
    }

    //댓글 쓰기
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, String username) {
        User user = getUser(username);
        postCheck(commentRequestDto);

        Comment comment = new Comment(commentRequestDto, user);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
//        return CommentResponseDto.builder()
//                .id(comment.getId())
//                .author(username)
//                .comment(comment.getComment())
//                .createAt(comment.getCreateAt())
//                .modifiedAt(comment.getModifiedAt())
//                .build();
    }

    //댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, String username) {
        User user = getUser(username);
        postCheck(commentRequestDto);

        Comment comment = commentRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("찾으시는 댓글이 없습니다"));

        if(!user.getUsername().equals(comment.getUser().getUsername())){
            throw new IllegalArgumentException("댓글 작성자가 다릅니다");
        }

        comment.update(commentRequestDto);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    //댓글 삭제
    @Transactional
    public String deleteComment(Long id, String username) {
        User user = getUser(username);

        Comment comment = commentRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("찾으시는 댓글이 없습니다"));

        if(!user.getUsername().equals(comment.getUser().getUsername())){
            throw new IllegalArgumentException("댓글 작성자가 다릅니다");
        }

        commentRepository.deleteById(id);
        return "댓글이 삭제되었습니다";
    }

    //댓글 전체목록 보기
    public List<CommentResponseDto> getCommentAllOfPost(Long id) {
        List<Comment> list = commentRepository.findAllByPostId(id);
        List<CommentResponseDto> clist = new ArrayList<>();
        for (Comment c : list) {
            //CommentResponseDto commentResponseDto = new CommentResponseDto(c);
            clist.add(new CommentResponseDto(c));
        }
        return clist;
    }
}
