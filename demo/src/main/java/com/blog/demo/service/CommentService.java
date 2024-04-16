package com.blog.demo.service;

import com.blog.demo.dto.CommentRequestDTO;
import com.blog.demo.dto.CommentResponseDTO;
import com.blog.demo.dto.UpdateCommentRequestDTO;
import com.blog.demo.model.Comment;
import com.blog.demo.model.VoteType;
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

import java.util.*;


@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;
    private final AuthService authService;



    public void deleteComment(Long commentId,Jwt jwt) {
        Comment commentRef = commentRepository.getReferenceById(commentId); //.orElseThrow(() -> new ErrorResponseException(HttpStatus.BAD_REQUEST,new Throwable("Comment doesn't exist")));
        User user = authService.getUserFromJwt(jwt);
        if(!user.equals(commentRef.getCommentor()))
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST,new Throwable("You are not authorized to delete this comment"));
        commentRef.setIsDeleted(true);
        commentRepository.save(commentRef);

    }
    public void updateComment(UpdateCommentRequestDTO updateCommentRequestDTO, Jwt jwt) {

        Comment commentRef = commentRepository.getReferenceById(updateCommentRequestDTO.getId());
        User user = authService.getUserFromJwt(jwt);
        if(!user.equals(commentRef.getCommentor()))
            throw new ErrorResponseException(HttpStatus.BAD_REQUEST,new Throwable("You are not authorized to delete this comment"));
        commentRef.setComment(updateCommentRequestDTO.getComment());
        commentRepository.save(commentRef);

    }
    public List<CommentResponseDTO> getComments(Long blogId) {
        List<CommentProjection> commentProjectionList = commentRepository.findAllComments(blogId);
        return getCommentReponse(commentProjectionList);
    }

    public List<CommentResponseDTO> getCommentReponse(List<CommentProjection> commentProjectionList) {

        Queue<CommentResponseDTO> queue = new LinkedList<>();
        List<CommentResponseDTO> rootComments = new LinkedList<>();
        HashMap<Long, CommentResponseDTO> map = new HashMap<>();
        HashMap<Long, List<CommentResponseDTO>> adjList = new HashMap<>();
        for(CommentProjection commentProjection : commentProjectionList) {
            CommentResponseDTO commentResponseDTO = new CommentResponseDTO(commentProjection);
            map.put(commentProjection.getId(),commentResponseDTO);
           if(commentProjection.getParentCommentId()==null) {
               rootComments.add(commentResponseDTO);
               queue.add(commentResponseDTO);
           }
           else {
                if(adjList.get(commentProjection.getParentCommentId())==null)
                     adjList.put(commentProjection.getParentCommentId(),new ArrayList<>());
               adjList.get(commentProjection.getParentCommentId()).add(commentResponseDTO);
           }
        }
        while(!queue.isEmpty())
        {
            CommentResponseDTO temp = queue.poll();
            if(adjList.get(temp.getId())!=null)
            {
                List<CommentResponseDTO> replies = adjList.get(temp.getId());
                temp.setReplies(replies);

                for(CommentResponseDTO reply : replies)
                    queue.add(reply);
            }
        }
        return rootComments ;
    }


    public Long createComment(CommentRequestDTO commentRequestDTO, Jwt jwt) {

        log.info(commentRequestDTO.toString());
        User commenter = authService.getUserFromJwt(jwt);
        Blog blog = blogRepository.getReferenceById(commentRequestDTO.getBlogId());
        Comment parentComment = null;
        if(commentRequestDTO.getParentCommentId() != null)
            parentComment =commentRepository.getReferenceById(commentRequestDTO.getParentCommentId());
        Comment comment = Comment.builder()
                .comment(commentRequestDTO.getComment())
                .createdDate(new Date())
                .updatedDate(new Date())
                .commentor(commenter)
                .blog(blog)
                .parentComment(parentComment)
                .build();

        Long  id =   commentRepository.save(comment).getId();
        Long totalComments  = commentRepository.countByBlogId(blog.getId());
        blog.setCommentCount(totalComments.intValue());
        blogRepository.save(blog);
        return id;

    }

}
