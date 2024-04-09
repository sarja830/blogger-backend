package com.blog.demo.model.blog;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Document(value = "blog-content")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BlogContent {
    @Id
    private String id;
    @Lob
    private String content;


    private List<String> tags;
    private List<String> images;

    private Date created;
    private Date lastUpdated;
//


}


