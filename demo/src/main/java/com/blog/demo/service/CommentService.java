package com.blog.demo.service;

import com.blog.demo.dto.CommentRequestDTO;
import com.blog.demo.model.Comment;
import com.blog.demo.model.blog.Blog;
import com.blog.demo.model.user.User;
import com.blog.demo.projection.CommentProjection;
import com.blog.demo.repository.BlogRepository;
import com.blog.demo.repository.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponseException;

import java.util.Date;
import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;
    private final AuthService authService;



    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
    public List<CommentProjection> getComments(Long blogId) {
//        return commentRepository.findCommentWithParentCommentId(blogId);
//        return commentRepository.findByBlogId(blogId);
        return commentRepository.findCommentWithParentCommentId(blogId);
    }

    public Comment createComment(CommentRequestDTO commentRequestDTO, Jwt jwt) {
        User commenter = authService.getUserFromJwt(jwt);
        Blog blog = blogRepository.findById(commentRequestDTO.getBlogId()).orElseThrow(() -> new ErrorResponseException(HttpStatus.CONFLICT,new Throwable("Blog not found")));
        Comment parentComment = null;
        if(commentRequestDTO.getParentCommentId() != null)
            parentComment = commentRepository.findById(commentRequestDTO.getParentCommentId()).orElseThrow(() -> new ErrorResponseException(HttpStatus.CONFLICT,new Throwable("Parent Comment not found")));
        Comment comment = Comment.builder()
                .comment(commentRequestDTO.getComment())
                .createdDate(new Date())
                .updatedDate(new Date())
                .commentor(commenter)
                .blog(blog)
                .parentComment(parentComment)
                .build();
        return  commentRepository.save(comment);
    }

}
