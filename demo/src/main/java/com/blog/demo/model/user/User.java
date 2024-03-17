package com.blog.demo.model.user;


import com.blog.demo.model.Vote;
import com.blog.demo.model.blog.Blog;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "`User`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "name is mandatory.")
    private String name;
    @NotBlank(message = "username is mandatory.")
    private String username;
    @NotBlank(message = "email is mandatory.")
    private String email;
    private String password;
    private Instant created;
    private boolean enabled;
    @Column(name = "role_type")
    private RoleType roleType;

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Blog> blogs;
//
//     i dont want comments by user
//     its better to have a unidirectional relationship  Instead replace one direction with an explicit query in you DAO/Repository. Keeps the model simpler and if done correctly via interfaces the application clean of circular dependencies
//    #TODO can it be list
//    @OneToMany(mappedBy = "blog")
//    Set<Vote> blogLikes;
}
