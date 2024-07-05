package com.gpt_hub.domain.gptdata.service;

import com.gpt_hub.common.exception.custom.NotEnoughPointException;
import com.gpt_hub.domain.gptdata.dto.GptDataResponse;
import com.gpt_hub.domain.gptdata.dto.GptDtoUtil;
import com.gpt_hub.domain.gptdata.dto.GptQuestion;
import com.gpt_hub.domain.gptdata.dto.api.GptRequest;
import com.gpt_hub.domain.gptdata.dto.api.GptResponse;
import com.gpt_hub.domain.gptdata.dto.api.Message;
import com.gpt_hub.domain.gptdata.entity.GptData;
import com.gpt_hub.domain.gptdata.repository.GptDataRepository;
import com.gpt_hub.domain.pointpocket.entity.PointPocket;
import com.gpt_hub.domain.pointpocket.service.PointPocketSearchService;
import com.gpt_hub.domain.prompt.entity.Prompt;
import com.gpt_hub.domain.prompt.service.PromptSearchService;
import com.gpt_hub.domain.user.entity.User;
import com.gpt_hub.domain.user.service.UserSearchService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    private final PointPocketSearchService pointPocketSearchService;
    private final RestTemplate restTemplate;

    private final BigDecimal dollarRate = new BigDecimal("1400");
    private final BigDecimal gptRequestTokenPrice = new BigDecimal("0.000001");
    private final BigDecimal gptResponseTokenPrice = new BigDecimal("0.000002");
    private final BigDecimal basePoint = new BigDecimal("10");

    @Value("${gpt.model}")
    private String model;

    @Value("${gpt.api.url}")
    private String apiUrl;

    public GptDataResponse askGpt(Long loginUserId, GptQuestion gptQuestion) {
        BigDecimal totalPoints = pointPocketSearchService.findTotalPointsByUserId(loginUserId);
        if (totalPoints.compareTo(basePoint) < 0) {
            throw new NotEnoughPointException("포인트가 부족합니다.");
        }

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
        int requestTokenUsage = gptResponse.getUsage().getPrompt_tokens();
        int responseTokenUsage = gptResponse.getUsage().getCompletion_tokens();

        BigDecimal pointUsage = pointUsageCalculator(requestTokenUsage, responseTokenUsage);

        Optional<PointPocket> findEarningPocketOptional = pointPocketSearchService.getEarningPocket(loginUserId);
        if (findEarningPocketOptional.isPresent()) {
            if (findEarningPocketOptional.get().getPoints().compareTo(pointUsage) >= 0) {
                findEarningPocketOptional.get().addPoints(pointUsage.negate().setScale(6, RoundingMode.HALF_UP));
            } else {
                BigDecimal remainingPoints = pointUsage.subtract(findEarningPocketOptional.get().getPoints());

                List<PointPocket> paymentsPockets =
                        pointPocketSearchService.findPaymentPointPocketsByUserId(loginUserId);

                for (PointPocket paymentPocket : paymentsPockets) {
                    if (paymentPocket.getPoints().compareTo(remainingPoints) >= 0) {
                        paymentPocket.addPoints(remainingPoints.negate().setScale(6, RoundingMode.HALF_UP));
                        break;
                    } else {
                        remainingPoints = remainingPoints.subtract(paymentPocket.getPoints());
                        paymentPocket.addPoints(paymentPocket.getPoints().negate().setScale(6, RoundingMode.HALF_UP));
                    }
                }
            }
        }

        GptData gptData = new GptData(
                findUser, findPrompt, finalQuestion, gptAnswer, requestTokenUsage, responseTokenUsage, pointUsage
        );

        GptData savedGptData = gptDataRepository.save(gptData);

        return GptDtoUtil.GptDataToGptDataResponse
                (
                        savedGptData,
                        findUser.getId(),
                        findPrompt == null ? null : findPrompt.getId(),
                        requestTokenUsage,
                        responseTokenUsage,
                        pointUsage
                );
    }

    private BigDecimal pointUsageCalculator(int requestTokenUsage, int responseTokenUsage) {
        BigDecimal requestTokenPrice = dollarRate.multiply(gptRequestTokenPrice).multiply(BigDecimal.valueOf(
                requestTokenUsage));
        BigDecimal responseTokenPrice = dollarRate.multiply(gptResponseTokenPrice).multiply(BigDecimal.valueOf(
                responseTokenUsage));
        BigDecimal totalUsage = requestTokenPrice.add(responseTokenPrice);
        return totalUsage.setScale(6, RoundingMode.HALF_UP);
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

    public void deleteGptData(Long gptDataId) {
        GptData findGptData = gptDataSearchService.findById(gptDataId);
        findGptData.delete();
    }
}
