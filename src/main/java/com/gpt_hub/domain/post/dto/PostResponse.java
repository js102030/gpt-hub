package com.gpt_hub.domain.post.dto;

import com.gpt_hub.domain.post.enumtype.Forum;
import lombok.Data;

@Data
public class PostResponse {

    private Long postId;
    private Long userId;
    private Long gptDataId;
    private String title;
    private String body;
    private Forum forum;
    private int hits;
    private boolean isDeleted;

}
