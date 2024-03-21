package com.blog.demo.repository;

import com.blog.demo.model.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    Optional<Blog> findById(Long id);
    Optional<Blog> findByName(String name);
    List<Blog> findAll();
    List<Blog>  findAllByParentId(Long parent_id);

}
