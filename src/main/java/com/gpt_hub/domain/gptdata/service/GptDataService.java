package com.gpt_hub.domain.gptdata.service;

import com.gpt_hub.domain.gptdata.dto.GptQuestion;
import com.gpt_hub.domain.gptdata.dto.GptRequest;
import com.gpt_hub.domain.gptdata.dto.GptResponse;
import com.gpt_hub.domain.gptdata.dto.Message;
import com.gpt_hub.domain.gptdata.entity.GptData;
import com.gpt_hub.domain.prompt.entity.Prompt;
import com.gpt_hub.domain.prompt.service.PromptSearchService;
import java.util.ArrayList;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@RequiredArgsConstructor
public class GptDataService {

    private final GptDataSearchService gptDataSearchService;
    private final PromptSearchService promptSearchService;
    private final RestTemplate restTemplate;

    @Value("${gpt.model}")
    private String model;

    @Value("${gpt.api.url}")
    private String apiUrl;

    public GptResponse askGpt(GptQuestion gptQuestion) {

        Prompt findPrompt;
        String prompt = "";
        if (gptQuestion.getPromptId() != null) {
            findPrompt = promptSearchService.findById(gptQuestion.getPromptId());
            prompt = findPrompt.getBody();
        }
        final String finalQuestion = prompt + gptQuestion.getQuestionBody();

        GptRequest request = GptRequest.builder()
                .model(model)
                .messages(new ArrayList<>(Collections.singletonList(
                        new Message("user", finalQuestion)
                )))
                .temperature(1)
                .maxTokens(300)
                .topP(1)
                .frequencyPenalty(0)
                .presencePenalty(0)
                .build();

        return restTemplate.postForObject(
                apiUrl
                , request
                , GptResponse.class
        );
    }

    public void deleteGptData(Long gptDataId) {
        GptData findGptData = gptDataSearchService.findById(gptDataId);
        findGptData.delete();
    }
}
