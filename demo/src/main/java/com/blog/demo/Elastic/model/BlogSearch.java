package com.blog.demo.Elastic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "blogindex")
public class BlogSearch {
    @Id
    private Long id;
    @Field(type = FieldType.Text, name = "title")
    private String title;
    @Lob
    @Field(type = FieldType.Text, name = "content")
    private String content;
    @Lob
    @Field(type = FieldType.Text, name = "description")
    private String description;
    @Field(type = FieldType.Boolean, name = "draft")
    private Boolean draft;
    @Field(type = FieldType.Long, name = "authorId")
    private Long authorId;
    @Transient
    private String profileImage;
    @Field(type = FieldType.Text, name = "name")
    private String name;
    @Field(type = FieldType.Text, name = "username")
    private String username;
    @Field(type = FieldType.Long, name = "categoryId")
    private Long categoryId;
    @Field(type = FieldType.Keyword, name = "categoryName")
    private String categoryName;

    private  Integer voteCount;

    private  Integer commentCount;

    private  Integer viewCount;
    private String banner;
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private Date created;
    @Field(type = FieldType.Keyword, name = "tags")
    private List<String> tags;
}
