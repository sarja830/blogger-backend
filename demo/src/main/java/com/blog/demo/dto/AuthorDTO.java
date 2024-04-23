package com.blog.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthorDTO {
    Long id;
    private String username;
    private String name;

    private String email;
    private String profileImage;
}
