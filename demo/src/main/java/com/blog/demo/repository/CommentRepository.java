package com.blog.demo.repository;

import com.blog.demo.model.Comment;
import com.blog.demo.model.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
