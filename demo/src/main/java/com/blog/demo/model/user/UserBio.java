package com.blog.demo.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserBio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(fetch=FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false)
    private User user;
    private String website;
    @Lob
    private String Bio;
    private String facebook;
    private String twitter;
    private String linkedin;
    private String github;
    private String instagram;
    private String youtube;

}
