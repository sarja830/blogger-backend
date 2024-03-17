package com.blog.demo.repository;

import com.blog.demo.model.blog.BlogContent;
import org.springframework.data.mongodb.repository.MongoRepository;


// second generic argument is for unique identifier
public interface BlogContentRepository extends MongoRepository<BlogContent,String> {
}

