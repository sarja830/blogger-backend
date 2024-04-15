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
import java.util.Date;
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

    private String description;
    @Column(name = "vote_count")
    private Integer voteCount;
    @Column(name = "comment_count")
    private Integer commentCount;
    @Column(name = "view_count")
    private  Integer viewCount;
    private String banner;
//    private String content;

//    @Lob
//    @Nullable
//    private String description;
    @NotBlank(message = "title is mandatory.")
    private String title;

    private boolean draft;

    // name is the name of the column in the current table
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User author;
    private Date created;
    @Column(name = "last_updated")
    private Date lastUpdated;

//    @OneToMany(mappedBy = "blog")
//    Set<Vote> votes;

    // one to many unidirectional relationship
    // instead call the api in the front get all comments by blog id
//    @OneToMany (mappedBy = "blog")
//    private Set<Comment> comments;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

}


