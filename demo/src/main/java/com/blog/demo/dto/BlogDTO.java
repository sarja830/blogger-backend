package com.blog.demo.dto;


import com.blog.demo.model.Category;
import com.blog.demo.model.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogDTO {
        @NotBlank(message = "Blog title cannot be empty")
        private String title;
        private String banner;
        private Long id;

        @JsonProperty(value="des")
        private String description;
        private String ContentId;
        @Lob
        private String content;
        private List<String> tags;
        private List<String> images;
        private Boolean draft;

        @JsonProperty(value="category_id")
        private Long categoryId;


        private Integer voteCount;
        private Integer viewCount;
        private Integer commentCount;
        private Category category;
        private AuthorDTO author;

        private Date created;
        private Date lastUpdated;
}
