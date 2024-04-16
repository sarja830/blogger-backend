package com.blog.demo.service;

import com.blog.demo.dto.VoteDTO;

import com.blog.demo.model.BlogUserId;

import com.blog.demo.model.Vote;
import com.blog.demo.model.VoteType;
import com.blog.demo.model.blog.Blog;
import com.blog.demo.model.user.User;
import com.blog.demo.repository.BlogRepository;

import com.blog.demo.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;


@Service
@Slf4j
@AllArgsConstructor
@Transactional
//@Transactional is applied to every public individual method. Private and Protected methods are Ignored by Spring.
public class VoteService {
    private final VoteRepository voteRepository;
    private final AuthService authService;
    private final BlogRepository blogRepository;


    public void deleteVote(Long blogId,  Jwt jwt) {
        User user = authService.getUserFromJwt(jwt);
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ErrorResponseException(HttpStatus.BAD_REQUEST,new Throwable("Blog doesn't exist")));
        BlogUserId blogUserId = new BlogUserId(blogId, user.getId());
        voteRepository.deleteById(blogUserId);
        Long totalVotes  = voteRepository.countByBlogIdAndVoteType(blogId, VoteType.UPVOTE);
        blog.setVoteCount(totalVotes.intValue());
        blogRepository.save(blog);
    }

    public Optional<Vote> getVote( Long blogId, @AuthenticationPrincipal Jwt jwt) {
        User user = authService.getUserFromJwt(jwt);
        BlogUserId blogUserId = new BlogUserId(blogId, user.getId());
        Vote vote = voteRepository.findById(blogUserId).orElse(null);
        if(vote==null)
            return Optional.empty();
        vote.setBlog(null);
        vote.setVoter(null);
        return Optional.of(vote);

    }

    public void castVote( VoteDTO voteDTO, @AuthenticationPrincipal Jwt jwt) {
        User user = authService.getUserFromJwt(jwt);

        Blog blog = blogRepository.getReferenceById(voteDTO.getBlogId()); //.orElseThrow(() -> new ErrorResponseException(HttpStatus.BAD_REQUEST,new Throwable("Blog doesn't exist")));
        if(blog.getAuthor().getId().equals(user.getId()))
            throw new ErrorResponseException(HttpStatus.CONFLICT,new Throwable("you can not vote on your own blog"));

        BlogUserId blogUserId = new BlogUserId(blog.getId(), user.getId());
        Vote vote = voteRepository.findById(blogUserId).orElse(null);
        if(vote!=null )
        {
            if(vote.getVoteType().ordinal() == voteDTO.getVoteType()-1)
                throw  new ErrorResponseException(HttpStatus.CONFLICT, new Throwable("You have already voted"));
            vote.setVoteType(VoteType.values()[voteDTO.getVoteType() - 1]);

        }
        else {
            vote = Vote.builder()
                    .id(blogUserId)
                    .blog(blog)
                    .voter(user)
                    .voteType(VoteType.values()[voteDTO.getVoteType() - 1])
                    .build();
        }
        voteRepository.save(vote);
        Long totalVotes  = voteRepository.countByBlogIdAndVoteType(voteDTO.getBlogId(), VoteType.UPVOTE);
        blog.setVoteCount(totalVotes.intValue());
        blogRepository.save(blog);
    }


}
