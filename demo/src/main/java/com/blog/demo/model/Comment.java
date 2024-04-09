package com.blog.demo.model;


import com.blog.demo.model.blog.Blog;
import com.blog.demo.model.user.User;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String comment;

    private Date createdDate;
    private Date updatedDate;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parentCommentId;

    @OneToMany(mappedBy = "parentCommentId",
            cascade = { CascadeType.REMOVE, CascadeType.PERSIST} )
    private Set<Comment> children;
//    @OneToOne
//    @JoinColumn(name = "parent_id")
//    private Comment parentCommentId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User commentor;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "blog_id", referencedColumnName = "id", nullable = false)
    private Blog blog;




}
