package com.blog.demo.dto;

import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogRequestDTO {

    @NotBlank(message = "Blog title cannot be empty")
    private String title;
    private String banner;
    @JsonProperty(value="des")
    private String description;
    private String content;
    private List<String> tags;
    private Boolean draft;
    private Long id;


}
