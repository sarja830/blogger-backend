package com.blog.demo.repository;

import com.blog.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.blog.demo.model.blog.Blog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> findAllByAuthor(User user);
//    List<Blog> getTopByVoteCount();


}
