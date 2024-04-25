package com.blog.demo.dto;

import com.blog.demo.model.user.User;
import com.blog.demo.projection.CommentProjection;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
public class CommentResponseDTO {

    Long id;
    String comment;
    Instant createdDate;
    String timeAgo;
    String updatedTimeAgo;
    Instant updatedDate;
    User commentor;
    Boolean isDeleted;
    Boolean isUpdated;
    List<CommentResponseDTO> replies;

    public CommentResponseDTO() {}
    public CommentResponseDTO(CommentProjection commentProjection) {
        this.comment = commentProjection.getComment();
        this.id = commentProjection.getId();
        this.createdDate = commentProjection.getCreatedDate();
        this.timeAgo =  TimeAgo.using(createdDate.toEpochMilli());
        this.updatedDate = commentProjection.getUpdatedDate();
        this.updatedTimeAgo = TimeAgo.using(updatedDate.toEpochMilli());
        this.commentor = commentProjection.getCommentor();
        this.replies = new ArrayList<>();
        this.isDeleted = commentProjection.getIsDeleted();
        this.isUpdated = commentProjection.getIsUpdated();
    }
}
