package com.blog.demo.model.blog;


import com.blog.demo.model.Category;
import com.blog.demo.model.Vote;
import com.blog.demo.model.Comment;
import com.blog.demo.model.user.User;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity(name = "Blog")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Blog {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(name = "content_id")
    private String contentId;

    @Column(name = "vote_count")
    private Integer voteCount;
    @Column(name = "view_count")
    private  Integer viewCount;
    private String banner;
    private String content;

    @Lob
    @Nullable
    private String description;
    @NotBlank(message = "title is mandatory.")
    private String title;

    private boolean draft;

    // name is the name of the column in the current table
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User author;
    private Instant created;
    @Column(name = "last_updated")
    private Instant lastUpdated;

    @OneToMany(mappedBy = "blog")
    Set<Vote> votes;

    // one to many unidirectional relationship
    @OneToMany (mappedBy = "blog")
    private Set<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}


