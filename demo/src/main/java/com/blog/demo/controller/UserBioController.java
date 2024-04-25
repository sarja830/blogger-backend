package com.blog.demo.controller;
import com.blog.demo.dto.UserBioDTO;
import com.blog.demo.exceptions.Result;
import com.blog.demo.exceptions.StatusCode;

import com.blog.demo.service.UserBioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/bio")
public class UserBioController{

    private final UserBioService userBioService;


    @PutMapping("")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result updateBio(@RequestBody UserBioDTO userBioDTO,
                                @AuthenticationPrincipal Jwt jwt)
    {
        userBioService.updateUserBio(userBioDTO,jwt);
        return new Result(true, StatusCode.SUCCESS, "Bio updated successfully");
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Result getUserDetails(
            @RequestParam Long id)
    {
        return new Result(true, StatusCode.SUCCESS,"" , userBioService.getUserBio(id));
    }
    @PutMapping("/profile")
    @ResponseStatus(HttpStatus.CREATED)
    public Result updateProfile(
            @RequestParam(value="profileImage",required=false)  String profileImage,
            @RequestParam(value="username",required=false)  String username,
            @RequestParam(value="name",required=false)  String name,
            @AuthenticationPrincipal Jwt jwt )
    {
        return new Result(true, StatusCode.SUCCESS,"" ,
                userBioService.updateUserProfileImage(profileImage,jwt,username,name)
        );
    }

}
