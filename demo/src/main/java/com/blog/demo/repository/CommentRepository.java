package com.blog.demo.repository;

import com.blog.demo.model.Comment;
import com.blog.demo.model.blog.Blog;
import com.blog.demo.projection.CommentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    //this method causes unnecessary join queries
    List<CommentProjection> findByBlogId(Long blogId);

    @Query("SELECT c.id as id," +
            " c.comment as comment," +
            " c.createdDate as createdDate," +
            " c.updatedDate as updatedDate," +
            " c.parentComment.id as parentCommentId," +
            " c.commentor as commentor" +
            " FROM Comment c WHERE c.blog.id = :blogId")
    List<CommentProjection> findCommentWithParentCommentId(Long blogId);
}
