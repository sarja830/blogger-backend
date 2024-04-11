package com.blog.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class BlogUserId
        implements Serializable {

    @Column(name = "blog_id")
    private Long blogId;

    @Column(name = "user_id")
    private Long userId;


    //Getters omitted for brevity

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        BlogUserId that = (BlogUserId) o;
        return Objects.equals(blogId, that.blogId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blogId, userId);
    }
}
