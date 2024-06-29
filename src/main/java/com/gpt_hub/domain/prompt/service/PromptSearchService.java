package com.gpt_hub.domain.prompt.service;

import com.gpt_hub.common.exception.custom.NotFoundException;
import com.gpt_hub.domain.prompt.entity.Prompt;
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

}
