package com.User.demo.controller;

import com.blog.demo.exceptions.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("")
    public Result getUsers(HttpServletRequest request) {
        return new Result();
    }
    @PostMapping("")
    public Result postUser(HttpServletRequest request) {
        return new Result();
    }
//    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("")
    public Result deleteUser(HttpServletRequest request) {
        return new Result();
    }
    @PutMapping("")
    public Result updateUser(HttpServletRequest request) {
        return new Result();
    }
}
