package com.blog.demo.repository;

import com.blog.demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findById(Long id);
    Optional<Category> findByName(String name);
    List<Category> findAll();
    List<Category>  findAllByParentId(Long parent_id);

}
