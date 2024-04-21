package com.blog.demo.model.elastic;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "blogindex")
public class SearchBlog {
    @Id
    Long id;
    String title;
    String content;
    String description;
    List<String> tags;
}
