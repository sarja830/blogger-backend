package com.blog.demo.dto;

import com.blog.demo.model.Comment;
import com.blog.demo.model.blog.Blog;
import com.blog.demo.model.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDTO{
        private Long id;
        private String comment;
        private Instant createdDate;
        private Instant updatedDate;
        private Comment parentComment;
        private User commentor;
        private Blog blog;
}
