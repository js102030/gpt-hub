package com.gpt_hub.domain.prompt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PromptBody {

    @NotBlank
    private String body;
}
