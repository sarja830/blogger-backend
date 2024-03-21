package com.blog.demo.controller;

import com.blog.demo.dto.BlogDTO;
import com.blog.demo.exceptions.Result;
import com.blog.demo.exceptions.StatusCode;
import com.blog.demo.service.BlogService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;
    @GetMapping("")
    public Result getBlogs(@RequestParam(value = "blog_id",required = false) Long id) {
        return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.getBlog(id));
    }
    @PostMapping("")
    public Result createBlog(@RequestBody BlogDTO blogDTO) {
        return new Result(true, StatusCode.SUCCESS, "Blog created successfully", this.blogService.createBlog(blogDTO));
    }
//    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("")
    public Result deleteBlog(@RequestParam("blog_id") Long id) {
        blogService.deleteBlog(id);
        return new Result(true, StatusCode.SUCCESS, "Blog deleted successfully");
    }
    @PutMapping("")
    public Result updateBlog(@RequestBody BlogDTO blogDTO) {
        return new Result(true, StatusCode.SUCCESS, "Blog updated successfully", this.blogService.updateBlog(blogDTO));
    }
}
