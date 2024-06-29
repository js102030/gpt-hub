package com.gpt_hub.domain.gptdata.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.gpt_hub.domain.gptdata.dto.GptQuestion;
import com.gpt_hub.domain.gptdata.dto.GptResponse;
import com.gpt_hub.domain.gptdata.service.GptDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GptController {

    private final GptDataService gptDataService;

    @PostMapping("/ask-gpt")
    @ResponseStatus(OK)
    public GptResponse askGpt(@RequestBody @Valid GptQuestion gptQuestion) {
        return gptDataService.askGpt(gptQuestion);
    }

    @DeleteMapping("/ask-gpt/{gptDataId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteGptData(@PathVariable Long gptDataId) {
        gptDataService.deleteGptData(gptDataId);
    }

}
