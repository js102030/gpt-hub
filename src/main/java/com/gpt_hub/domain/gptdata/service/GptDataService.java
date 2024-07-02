package com.gpt_hub.domain.gptdata.service;

import com.gpt_hub.domain.gptdata.dto.GptDataResponse;
import com.gpt_hub.domain.gptdata.dto.GptDtoUtil;
import com.gpt_hub.domain.gptdata.dto.GptQuestion;
import com.gpt_hub.domain.gptdata.dto.api.GptRequest;
import com.gpt_hub.domain.gptdata.dto.api.GptResponse;
import com.gpt_hub.domain.gptdata.dto.api.Message;
import com.gpt_hub.domain.gptdata.entity.GptData;
import com.gpt_hub.domain.gptdata.repository.GptDataRepository;
import com.gpt_hub.domain.prompt.entity.Prompt;
import com.gpt_hub.domain.prompt.service.PromptSearchService;
import com.gpt_hub.domain.user.entity.User;
import com.gpt_hub.domain.user.service.UserSearchService;
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
    private final GptDataRepository gptDataRepository;
    private final PromptSearchService promptSearchService;
    private final UserSearchService userSearchService;
    private final RestTemplate restTemplate;

    @Value("${gpt.model}")
    private String model;

    @Value("${gpt.api.url}")
    private String apiUrl;

    public GptDataResponse askGpt(Long loginUserId, GptQuestion gptQuestion) {
        Prompt findPrompt = null;
        String prompt = "";
        if (gptQuestion.getPromptId() != null) {
            findPrompt = promptSearchService.findById(gptQuestion.getPromptId());
            prompt = findPrompt.getBody();
        }
        final String finalQuestion = prompt + gptQuestion.getQuestionBody();

        GptRequest request = createGptRequest(finalQuestion);
        GptResponse gptResponse = getGptResponse(request);

        User findUser = userSearchService.findById(loginUserId);
        String gptAnswer = gptResponse.getChoices().getFirst().getMessage().getContent();

        GptData gptData = new GptData(findUser, findPrompt, finalQuestion, gptAnswer);

        GptData savedGptData = gptDataRepository.save(gptData);

        return GptDtoUtil.GptDataToGptDataResponse
                (
                        savedGptData, findUser.getId(),
                        findPrompt == null ? null : findPrompt.getId()
                );
    }

    public void deleteGptData(Long gptDataId) {
        GptData findGptData = gptDataSearchService.findById(gptDataId);
        findGptData.delete();
    }

    private GptResponse getGptResponse(GptRequest request) {
        return restTemplate.postForObject(
                apiUrl
                , request
                , GptResponse.class
        );
    }

    private GptRequest createGptRequest(String finalQuestion) {
        return GptRequest.builder()
                .model(model)
                .messages(
                        new ArrayList<>(Collections.singletonList(new Message("user", finalQuestion)))
                )
                .temperature(1)
                .maxTokens(300)
                .topP(1)
                .frequencyPenalty(0)
                .presencePenalty(0)
                .build();
    }
}
