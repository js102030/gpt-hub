package com.gpt_hub.domain.prompt.service;

import com.gpt_hub.common.exception.custom.NotFoundException;
import com.gpt_hub.domain.prompt.dto.PromptResponse;
import com.gpt_hub.domain.prompt.entity.Prompt;
import com.gpt_hub.domain.prompt.mapper.PromptMapper;
import com.gpt_hub.domain.prompt.repository.PromptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PromptSearchService {

    private final PromptRepository promptRepository;

    public Prompt findById(Long promptId) {
        return promptRepository.findById(promptId)
                .orElseThrow(
                        () -> new NotFoundException(String.format("promptId %d 에 해당하는 Prompt 를 찾을 수 없습니다.", promptId)));
    }

    public Prompt findByIdAndUserId(Long promptId, Long userId) {
        return promptRepository.findByIdAndUserId(promptId, userId)
                .orElseThrow(
                        () -> new NotFoundException(String.format("promptId %d 에 해당하는 Prompt 를 찾을 수 없습니다.", promptId)));
    }

    public PromptResponse findByIdAndUserId_ADMIN(Long promptId, Long userId) {
        Prompt prompt = findByIdAndUserId(promptId, userId);
        return PromptMapper.INSTANCE.promptToPromptResponse(prompt);
    }

}
