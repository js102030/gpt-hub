package com.gpt_hub.domain.prompt.dto;

import lombok.Data;

@Data
public class PromptResponse {

    private Long id;
    private Long userId;
    private String body;
    private boolean isDeleted;

}
