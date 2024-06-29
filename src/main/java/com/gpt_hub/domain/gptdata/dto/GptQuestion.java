package com.gpt_hub.domain.gptdata.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GptQuestion {

    @NotBlank
    private String questionBody;

    private Long promptId;

}
