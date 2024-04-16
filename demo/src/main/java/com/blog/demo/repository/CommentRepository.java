package com.blog.demo.repository;

import com.blog.demo.model.Comment;
import com.blog.demo.model.blog.Blog;
import com.blog.demo.projection.CommentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {

    //this method causes unnecessary join queries
    List<Comment> findByBlogId(Long blogId);


    Long countByBlogId(Long blogId);
    @Override
    Optional<Comment> findById(Long blogId);

    @Query("SELECT c.id as id," +
            " c.comment as comment," +
            " c.isDeleted as isDeleted," +
            " c.createdDate as createdDate," +
            " c.updatedDate as updatedDate," +
            " c.parentComment.id as parentCommentId," +
            " c.commentor as commentor" +
            " FROM Comment c WHERE c.blog.id = :blogId order by c.createdDate asc ")
    List<CommentProjection> findAllComments(Long blogId);
}
