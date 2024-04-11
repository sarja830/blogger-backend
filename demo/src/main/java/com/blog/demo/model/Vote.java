package com.blog.demo.model;

import com.blog.demo.model.blog.Blog;
import com.blog.demo.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Vote")
public class Vote {

    @EmbeddedId
    private BlogUserId id;

    @ManyToOne
    @MapsId("blogId")
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User voter;

    private VoteType voteType;

}

