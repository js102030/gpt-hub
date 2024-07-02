package com.gpt_hub.domain.gptdata.dto.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GptRequest {

    private String model;
    private List<Message> messages;
    private int temperature;
    private int maxTokens;
    private float topP;
    private int frequencyPenalty;
    private int presencePenalty;

}