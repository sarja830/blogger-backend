package com.blog.demo.repository;

import com.blog.demo.model.blog.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

;
import java.util.*;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    Optional<Blog> findByIdAndDraft(Long id,Boolean draft);
    Optional<Blog> findByIdAndAuthorId(Long id,Long authorId);




    Page<Blog> findByDraft(Boolean draft, Pageable pageable);
    Long countByDraft(Boolean draft);
    Page<Blog> findByDraftAndAuthorIdIn(Boolean draft,List<Long> authorIds, Pageable pageable);
    Long countByDraftAndAuthorIdIn(Boolean draft,List<Long> authorIds);
    Page<Blog> findByDraftAndAuthorIdInAndCategoryIdIn(Boolean draft, List<Long> authorId, Set<Long> categoryIds, Pageable pageable);
    Long countByDraftAndAuthorIdInAndCategoryIdIn(Boolean draft,List<Long> authorId, Set<Long> categoryIds);
    Page<Blog> findByCategoryIdInAndDraft(Collection<Long> categoryIds, Boolean draft, Pageable pageable);
    Long countByCategoryIdInAndDraft(Collection<Long> categoryIds, Boolean draft);
    List<Blog> findAll();
    Page<Blog>  findByAuthor(Long authorId, Pageable pageable);

}
