package com.blog.demo.Elastic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogSearchDTO {
    Long id;
    String title;

    String description;
    Boolean draft;
    Long authorId;
    String profileImage;
    String name;
    String username;
    Long categoryId;
    String categoryName;
    String banner;
    Date created;
    List<String> tags;
}


