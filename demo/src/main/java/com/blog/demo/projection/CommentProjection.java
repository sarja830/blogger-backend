package com.blog.demo.projection;


import com.blog.demo.model.Comment;
import com.blog.demo.model.user.User;

import java.time.Instant;
import java.util.Date;

public interface CommentProjection {
    Long getId();
    String getComment();
    Instant getCreatedDate();
    Instant getUpdatedDate();
    Long getParentCommentId();
    Boolean getIsDeleted();
    Boolean getIsUpdated();
    User getCommentor();
    // Use the ID of parent comment
 // Add other necessary fields if needed
}
