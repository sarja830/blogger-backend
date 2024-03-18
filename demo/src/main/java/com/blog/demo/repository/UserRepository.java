package com.blog.demo.repository;

import com.blog.demo.model.Comment;
import com.blog.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
