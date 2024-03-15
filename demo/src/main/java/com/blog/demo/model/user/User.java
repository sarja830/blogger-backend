package com.blog.demo.model.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "name is mandatory.")
    private String name;
    @NotBlank(message = "email is mandatory.")
    private String email;
    private String password;
    private Instant created;
    private boolean enabled;
    private UserType userType;

}
