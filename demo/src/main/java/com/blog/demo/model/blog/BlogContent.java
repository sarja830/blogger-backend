package com.blog.demo.model.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(value = "blog-content")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BlogContent {
    @Id
    private String id;
    private String content;

}


