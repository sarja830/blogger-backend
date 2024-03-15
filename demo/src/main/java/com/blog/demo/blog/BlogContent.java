package com.blog.demo.blog;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "blog-content")
public class BlogContent {
    private String id;
    private String title;
    private String content;
    private String author;
    private String date;

}
