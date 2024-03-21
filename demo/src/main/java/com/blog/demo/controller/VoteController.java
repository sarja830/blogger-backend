package com.blog.demo.controller;

import com.blog.demo.exceptions.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    @GetMapping("")
    public Result getBlogs(HttpServletRequest request) {
        return new Result();
    }
    @PostMapping("")
    public Result postBlog(HttpServletRequest request) {
       return new Result();

    }
//    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("")
    public Result deleteBlog(HttpServletRequest request) {
        return new Result();
    }
    @PutMapping("")
    public Result updateBlog(HttpServletRequest request) {
        return new Result();
    }
}
