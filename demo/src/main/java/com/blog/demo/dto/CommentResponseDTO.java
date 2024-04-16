package com.blog.demo.dto;

import com.blog.demo.model.user.User;
import com.blog.demo.projection.CommentProjection;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
public class CommentResponseDTO {

    Long id;
    String comment;
    Date createdDate;
    Date updatedDate;
    User commentor;
    Boolean isDeleted;
    List<CommentResponseDTO> replies;

    public CommentResponseDTO() {}
    public CommentResponseDTO(CommentProjection commentProjection) {
        this.comment = commentProjection.getComment();
        this.id = commentProjection.getId();
        this.createdDate = commentProjection.getCreatedDate();
        this.updatedDate = commentProjection.getUpdatedDate();
        this.commentor = commentProjection.getCommentor();
        this.replies = new ArrayList<>();
        this.isDeleted = commentProjection.getIsDeleted();
    }
}
