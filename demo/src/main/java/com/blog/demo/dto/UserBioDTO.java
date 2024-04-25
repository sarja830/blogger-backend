package com.blog.demo.dto;

import com.blog.demo.model.user.User;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class UserBioDTO {
    private String username;
    private String name;
    private Date created;
    private String email;
    private String profileImage;

    private String website;
    private String bio;
    private String facebook;
    private String twitter;
    private String linkedin;
    private String github;
    private String instagram;
    private String youtube;
}
