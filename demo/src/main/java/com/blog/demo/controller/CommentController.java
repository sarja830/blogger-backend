package com.blog.demo.controller;

import com.blog.demo.exceptions.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments/")
public class CommentController {

    @GetMapping("")
    public Result getComments(HttpServletRequest request) {
        return new Result();
    }
    @PostMapping("")
    public Result postComment(HttpServletRequest request) {
        return new Result();
    }
//    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("")
    public Result deleteComment(HttpServletRequest request) {
        return new Result();
    }
    @PutMapping("")
    public Result updateComment(HttpServletRequest request) {
        return new Result();
    }
}
