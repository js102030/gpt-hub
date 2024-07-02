package com.gpt_hub.domain.comment.dto;

import lombok.Data;

@Data
public class CommentResponse {

    private Long commentId;
    private Long userId;
    private Long postId;
    private String body;
    private boolean isDeleted;

}
