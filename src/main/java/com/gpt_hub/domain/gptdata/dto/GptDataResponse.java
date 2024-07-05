package com.gpt_hub.domain.gptdata.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GptDataResponse {

    private Long gptDataId;
    private Long userId;
    private Long promptId;
    private String question;
    private String answer;
    private String category;
    private boolean isDeleted;
    private int requestTokenUsage;
    private int responseTokenUsage;
    private BigDecimal pointUsage;

}
