package com.blog.demo.model.user;


import com.blog.demo.dto.Views;
import com.blog.demo.model.Vote;
import com.blog.demo.model.blog.Blog;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "`User`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "name in model is mandatory.")
    private String name;
    @NotBlank(message = "username is mandatory.")
    @Column(unique=true)
    private String username;
    @NotBlank(message = "email is mandatory.")
    @Column(unique=true)
    private String email;


    private String profileImage = "";

    @JsonView(Views.Internal.class)

    private String password;
    private Date created;
    private boolean enabled;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role_type")
    private RoleType roleType;

//    @OneToMany(
//            mappedBy = "author",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    List<Blog> blogs;


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        User that = (User) o;
        return Objects.equals(id, this.id);

    }
}
