package com.blog.demo.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@Builder
public class VoteDTO {

    @NotNull(message = "Blog id can not be null")
    private Long blogId;

    @NotNull(message = "Vote type can not be null")
    private Integer voteType;


}


