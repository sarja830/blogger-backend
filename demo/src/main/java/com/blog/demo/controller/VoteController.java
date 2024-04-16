package com.blog.demo.controller;

import com.blog.demo.dto.VoteDTO;
import com.blog.demo.exceptions.Result;
import com.blog.demo.exceptions.StatusCode;
import com.blog.demo.service.VoteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/votes")
public class VoteController {
    private final VoteService voteService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Result castVoteOnBlog(@RequestBody VoteDTO voteDTO, @AuthenticationPrincipal Jwt jwt)
    {
        voteService.castVote(voteDTO,jwt);
        return new Result(true, StatusCode.SUCCESS, "Vote Casted successfully");
    }
    @GetMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Result getVotesOfBlog(
            @RequestParam(value="blogId",required=true) Long blogId,
            @AuthenticationPrincipal Jwt jwt)
    {
        return new Result(true, StatusCode.SUCCESS,"" , voteService.getVote(blogId,jwt).orElse(null));
    }

    @DeleteMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Result removeVote(@RequestParam  Long blogId, @AuthenticationPrincipal Jwt jwt)
    {
        voteService.deleteVote(blogId,jwt);
        return new Result(true, StatusCode.SUCCESS, "Vote Deleted successfully");
    }
}
