package com.blog.demo.dto;

import com.blog.demo.model.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {
        @NotBlank(message = "Blog title cannot be empty")
        private String title;
        private String banner;
        @JsonProperty(value="des")
        private String description;
        @JsonProperty(value="content_id")
        private String ContentId;
        @Lob
        private String content;
        private List<String> tags;
        private List<String> images;
        private Boolean draft;
        private Category category;
}
