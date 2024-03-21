package com.blog.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDTO {
        private Long id;
        @JsonProperty(value="blog_id")
        private Long blogId;
        private Instant createdDate;
        @NotBlank
        private String comment;
        @JsonProperty(value="user_id")
        private String userId;
    }

