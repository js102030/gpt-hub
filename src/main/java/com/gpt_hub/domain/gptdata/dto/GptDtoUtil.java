package com.gpt_hub.domain.gptdata.dto;

import com.gpt_hub.domain.gptdata.entity.GptData;

public class GptDtoUtil {

    public static GptDataResponse GptDataToGptDataResponse(GptData gptData, Long userId, Long promptId) {
        return GptDataResponse.builder()
                .gptDataId(gptData.getId())
                .userId(userId)
                .promptId(promptId)
                .question(gptData.getQuestion())
                .answer(gptData.getAnswer())
                .category(gptData.getCategory())
                .isDeleted(gptData.isDeleted())
                .build();
    }
}
