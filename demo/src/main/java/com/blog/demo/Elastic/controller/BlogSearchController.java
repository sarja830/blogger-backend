package com.blog.demo.Elastic.controller;

import com.blog.demo.Elastic.model.BlogSearch;
import com.blog.demo.Elastic.service.BlogSearchService;
import com.blog.demo.dto.BlogDTO;
import com.blog.demo.exceptions.Result;
import com.blog.demo.exceptions.StatusCode;
import com.blog.demo.model.blog.Blog;
import com.blog.demo.service.BlogService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/search")
public class BlogSearchController {
    private final BlogSearchService blogSearchService;

    @GetMapping("")
    public Result search(@RequestParam(value = "query") String query,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        return new Result(true, StatusCode.SUCCESS, "Search results",
                blogSearchService.search(query,page,pageSize));
    }
    @GetMapping("/count")
    public Result searchCount(@RequestParam(value = "query") String query) {
        return new Result(true, StatusCode.SUCCESS, "Search results",
                blogSearchService.searchResultCount(query));
    }
    @GetMapping("/users")
    public Result searchUser(@RequestParam(value = "query") String query,
                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize)
    {
        return new Result(true, StatusCode.SUCCESS, "Search results",
                blogSearchService.searchByUser(query,page,pageSize));
    }
    @GetMapping("/users/count")
    public Result searchUserCount(@RequestParam(value = "query") String query)
    {
        return new Result(true, StatusCode.SUCCESS, "Search results count",
                blogSearchService.countByUser(query));
    }

    @PostMapping("")
    public void search( @RequestParam Long id) {
        blogSearchService.sync(id);
    }

}
