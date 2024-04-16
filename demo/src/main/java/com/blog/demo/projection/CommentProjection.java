package com.blog.demo.projection;


import com.blog.demo.model.Comment;
import com.blog.demo.model.user.User;

import java.util.Date;

public interface CommentProjection {
    Long getId();
    String getComment();
    Date getCreatedDate();
    Date getUpdatedDate();
    Long getParentCommentId();
    Boolean getIsDeleted();
    User getCommentor();
    // Use the ID of parent comment
 // Add other necessary fields if needed
}
