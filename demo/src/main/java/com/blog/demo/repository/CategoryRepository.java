package com.blog.demo.repository;

import com.blog.demo.model.Category;
import com.blog.demo.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryRepository extends JpaRepository<Vote, Long> {

    @Query(value = "select n from Category n inner join n.parentCategory p where p.id = ?1")
    List<Category> findNodesByParentId(Long parentId);
}
