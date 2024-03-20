package com.blog.demo.repository;

import com.blog.demo.model.VerificationToken;
import com.blog.demo.model.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
    Optional<VerificationToken> findByUser(User user);
    Optional<VerificationToken> findByUserAndToken(User user , String token);


    @Modifying
    @Transactional
    @Query("DELETE from VerificationToken t WHERE t.user.id = :user_id")
    void deleteByUser(@Param("user_id") Long id);

}

