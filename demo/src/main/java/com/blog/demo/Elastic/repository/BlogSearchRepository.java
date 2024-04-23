package com.blog.demo.Elastic.repository;

import com.blog.demo.Elastic.dto.BlogSearchDTO;
import com.blog.demo.Elastic.model.BlogSearch;
import com.blog.demo.dto.AuthorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogSearchRepository extends ElasticsearchRepository<BlogSearch, String> {
//    List<BlogSearch> findByContentOrDescriptionOrTitle(String content, String description, String title);
//    @Query("{\"query\": {\"multi_match\": {\"query\": \"?0\", \"fields\": [\"title\", \"description\",\"content\"], \"fuzziness\": \"AUTO\"}}, \"_source\": {\"excludes\": [\"content\"]}}")
    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"title^3\", \"description\",\"content\"], \"fuzziness\": \"AUTO\"}}")
    Page<BlogSearch> findByTitleOrContentOrDescriptionUsingCustomQuery(String query, Pageable pageable);

    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"name^3\", \"username\"], \"fuzziness\": \"AUTO\"}}")
    Page<BlogSearch> findUser(String query, Pageable pageable);

}

