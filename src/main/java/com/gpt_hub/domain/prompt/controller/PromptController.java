package com.gpt_hub.domain.prompt.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.gpt_hub.common.annotation.LoginUserId;
import com.gpt_hub.domain.prompt.dto.PromptBody;
import com.gpt_hub.domain.prompt.dto.PromptResponse;
import com.gpt_hub.domain.prompt.service.PromptSearchService;
import com.gpt_hub.domain.prompt.service.PromptService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PromptController {

    private final PromptService promptService;
    private final PromptSearchService promptSearchService;

    @ResponseStatus(CREATED)
    @PostMapping("/prompts")
    public PromptResponse savePrompt(@LoginUserId Long loginUserId, @RequestBody @Valid PromptBody promptBody) {
        return promptService.savePrompt(loginUserId, promptBody.getBody());
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/prompts/{promptId}")
    public void deletePrompt(@LoginUserId Long loginUserId, @PathVariable Long promptId) {
        promptService.deletePrompt(loginUserId, promptId);
    }

    @ResponseStatus(OK)
    @GetMapping("/admin/prompts/{promptId}") // 삭제된 프롬프트도 조회
    public PromptResponse getPrompt(@LoginUserId Long loginUserId, @PathVariable Long promptId) {
        return promptSearchService.findByIdAndUserId_ADMIN(promptId, loginUserId);
    }

}
