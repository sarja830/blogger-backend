package com.blog.demo.controller;

import com.blog.demo.dto.CommentRequestDTO;
import com.blog.demo.dto.UpdateCommentRequestDTO;
import com.blog.demo.dto.VoteDTO;
import com.blog.demo.exceptions.Result;
import com.blog.demo.exceptions.StatusCode;
import com.blog.demo.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Result postComment(@RequestBody CommentRequestDTO commentRequestDTO, @AuthenticationPrincipal Jwt jwt)
    {
        return new Result(true, StatusCode.SUCCESS, "comment posted successfully",commentService.createComment(commentRequestDTO,jwt));
    }



    @DeleteMapping("")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result deleteComment(@RequestParam Long id,@AuthenticationPrincipal Jwt jwt)
    {
        commentService.deleteComment(id,jwt);
        return new Result(true, StatusCode.SUCCESS, "comment posted successfully");
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result updateComment(@RequestBody UpdateCommentRequestDTO updateCommentRequestDTO,
                                @AuthenticationPrincipal Jwt jwt)
    {
        commentService.updateComment(updateCommentRequestDTO,jwt);
        return new Result(true, StatusCode.SUCCESS, "comment posted successfully");
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Result getCommentsOnBlog(
            @RequestParam(value="blogId",required=true) Long blogId,
            @AuthenticationPrincipal Jwt jwt)
    {
        return new Result(true, StatusCode.SUCCESS,"" , commentService.getComments(blogId));
    }

}
