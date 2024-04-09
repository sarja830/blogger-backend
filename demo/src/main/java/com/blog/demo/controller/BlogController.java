package com.blog.demo.controller;

import com.blog.demo.dto.BlogDTO;
import com.blog.demo.exceptions.Result;
import com.blog.demo.exceptions.StatusCode;
import com.blog.demo.model.user.User;
import com.blog.demo.service.BlogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;
    //    @GetMapping("")
//    public Result getBlogs(@RequestParam(value = "blog_id",required = false) Long id) {
//        return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.getBlog(id));
//    }
    @GetMapping("")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result getBlogsPublic(
            @RequestParam(value="page",required=true,defaultValue="0") Integer page,
            @RequestParam(value="page_size",required=true,defaultValue="5") Integer pageSize,
            @RequestParam(value="category_id",required=false) List<Integer> categoryIds,
            @RequestParam(value="author_id",required=false) List<Long> authorIds
    )
    {
        if(categoryIds==null && authorIds==null)
            return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.getBlogs(false,page,pageSize));
        else if(categoryIds!=null && authorIds!=null)
            return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.getBlogsByDraftAuthorCategory(false,authorIds,categoryIds,page,pageSize));
        else if(categoryIds!=null)
            return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.getBlogsByCategory(categoryIds,page,pageSize));
        else if(authorIds!=null)
            return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.getBlogsByAuthor(false,authorIds,page,pageSize));
        return null;

    }

    @GetMapping("/author")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result getBlogsByAuthor(
            @RequestParam(value="page",required=true,defaultValue="0") Integer page,
            @RequestParam(value="page_size",required=true,defaultValue="5") Integer pageSize,
            @RequestParam(value="category_id",required=false) List<Integer> categoryIds,
            @RequestParam(value="draft",required=true,defaultValue="true") Boolean draft,
            @AuthenticationPrincipal Jwt jwt
    )
    {

        User author = blogService.getUserFromJwt(jwt);
        List<Long> authorIds = List.of(author.getId());
        if(categoryIds==null && authorIds==null)
            return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.getBlogs(draft,page,pageSize));
        else if(categoryIds!=null && authorIds!=null)
            return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.getBlogsByDraftAuthorCategory(draft,authorIds,categoryIds,page,pageSize));
        else if(categoryIds!=null)
            return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.getBlogsByCategory(categoryIds,page,pageSize));
        else if(authorIds!=null)
            return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.getBlogsByAuthor(draft,authorIds,page,pageSize));
        return null;

    }

    @GetMapping("/author/count")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result getBlogCountAuthor(
            @RequestParam(value="category_id",required=false) List<Integer> categoryIds,
            @RequestParam(value="draft",required=true,defaultValue="true") Boolean draft,
            @AuthenticationPrincipal Jwt jwt
    )
    {
        User author = blogService.getUserFromJwt(jwt);
        List<Long> authorIds = List.of(author.getId());
        if(categoryIds==null && authorIds==null)
            return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.countBlogs(draft));
        else if(categoryIds!=null && authorIds!=null)
            return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.countBlogsByDraftAuthorCategory(draft,authorIds,categoryIds));
        else if(categoryIds!=null)
            return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.countBlogsByCategory(categoryIds));
        else if(authorIds!=null)
            return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.countBlogsByAuthor(draft,authorIds));
        return null;
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result getBlogCountAuthor(
            @RequestParam(value="category_id",required=false) List<Integer> categoryIds,
            @RequestParam(value="author_id",required=false) List<Long> authorIds
    )
    {
        if(categoryIds==null && authorIds==null)
            return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.countBlogs(false));
        else if(categoryIds!=null && authorIds!=null)
            return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.countBlogsByDraftAuthorCategory(false,authorIds,categoryIds));
        else if(categoryIds!=null)
            return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.countBlogsByCategory(categoryIds));
        else if(authorIds!=null)
            return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.countBlogsByAuthor(false,authorIds));
        return null;
    }
    @GetMapping("/blogId/{blogId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result getBlogById(@PathVariable  long blogId) {
        return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.getBlogByIdPublic(blogId));
    }

    @GetMapping("/author/blogId/{blogId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result getBlogByIdByAuthor(@PathVariable  long blogId,
            @AuthenticationPrincipal Jwt jwt)
    {
        User author = blogService.getUserFromJwt(jwt);
        return new Result(true, StatusCode.SUCCESS, "Fetched blog hierarchy successfully",this.blogService.getBlogByIdByAuthor(blogId,author.getId()));
    }
    @PostMapping("")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result createBlog(@RequestBody BlogDTO blogDTO,@AuthenticationPrincipal Jwt jwt) {
        log.info(jwt.toString());
        jwt.getClaims().forEach((k,v)->{
            log.info(k+" : "+v);
        });
        return new Result(true, StatusCode.SUCCESS, "Blog created successfully", this.blogService.createBlog(blogDTO,jwt));
    }
    //    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Result deleteBlog(@RequestParam("blog_id") Long id,@AuthenticationPrincipal Jwt jwt) {
        blogService.deleteBlog(id,jwt);
        return new Result(true, HttpStatus.CREATED.value(), "Blog deleted successfully");
    }
    @PutMapping("")
    public Result updateBlog(@RequestBody BlogDTO blogDTO,@AuthenticationPrincipal Jwt jwt) {
        return new Result(true, StatusCode.SUCCESS, "Blog updated successfully", this.blogService.updateBlog(blogDTO,jwt));
    }
}
