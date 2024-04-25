package com.blog.demo.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    private String authenticationToken;
    private Long id;
    private String profileImage;
    private String refreshToken;
    private Instant expiresAt;
    private String username;
    private String name;
    private String role;
}

