package com.blog.demo.repository;

import com.blog.demo.model.user.User;
import com.blog.demo.model.user.UserBio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Repository
@Transactional
public interface UserBioRepository extends JpaRepository<UserBio, Long> {


    Optional<UserBio> findByUserId(Long id);

//    @Query("SELECT c.id as id," +
//            " c.comment as comment," +
//            " c.isDeleted as isDeleted," +
//            " c.createdDate as createdDate," +
//            " c.updatedDate as updatedDate," +
//            " c.parentComment.id as parentCommentId," +
//            " c.commentor as commentor" +
//            " FROM Comment c WHERE c.blog.id = :blogId order by c.createdDate asc ")
//    List<CommentProjection> findAllComments(Long blogId);
}
