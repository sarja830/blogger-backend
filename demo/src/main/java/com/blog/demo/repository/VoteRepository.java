package com.blog.demo.repository;

import com.blog.demo.model.BlogUserId;

import com.blog.demo.model.Vote;
import com.blog.demo.model.VoteType;
import com.blog.demo.model.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository  extends JpaRepository<Vote, BlogUserId> {
    void deleteById(BlogUserId blogUserId);
    Long countByBlogIdAndVoteType(Long blogId, VoteType voteType);
    Optional<Vote> findById(BlogUserId blogVoteId);
}

