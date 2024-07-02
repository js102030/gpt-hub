package com.gpt_hub.domain.post.dto;

import com.gpt_hub.domain.post.enumtype.Forum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    @NotNull
    private Forum forum;

    private Long gptDataId;

}
