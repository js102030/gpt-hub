package com.gpt_hub.domain.prompt.service;

import com.gpt_hub.domain.prompt.dto.PromptResponse;
import com.gpt_hub.domain.prompt.entity.Prompt;
import com.gpt_hub.domain.prompt.mapper.PromptMapper;
import com.gpt_hub.domain.prompt.repository.PromptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PromptService {

    private final PromptRepository promptRepository;
    private final PromptSearchService promptSearchService;

    public PromptResponse savePrompt(Long loginUserId, String promptBody) {
        Prompt newPrompt = new Prompt(loginUserId, promptBody);

        Prompt savedPrompt = promptRepository.save(newPrompt);

        return PromptMapper.INSTANCE.promptToPromptResponse(savedPrompt);
    }

    public void deletePrompt(Long loginUserId, Long promptId) {
        Prompt findPrompt = promptSearchService.findByIdAndUserId(promptId, loginUserId);

        findPrompt.delete();
    }
}
