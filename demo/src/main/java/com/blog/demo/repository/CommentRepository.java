package com.blog.demo.repository;

import com.blog.demo.model.Comment;
import com.blog.demo.model.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
