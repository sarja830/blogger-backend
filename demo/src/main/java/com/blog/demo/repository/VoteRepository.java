package com.blog.demo.repository;

import com.blog.demo.model.Vote;
import com.blog.demo.model.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository  extends JpaRepository<Vote, Long> {
}
