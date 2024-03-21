package com.blog.demo.controller;

import com.blog.demo.exceptions.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories/")
public class CategoryController {

    @GetMapping("")
    public Result getCategories(HttpServletRequest request) {
        return new Result();

    }
    @PostMapping("")
    public Result postCategory(HttpServletRequest request) {
        return new Result();
    }
//    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("")
    public Result deleteCategory(HttpServletRequest request) {
        return new Result();

    }
    @PutMapping("")
    public Result updateCategory(HttpServletRequest request) {
        return new Result();

    }
}
