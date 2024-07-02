package com.gpt_hub.domain.prompt.mapper;

import com.gpt_hub.domain.prompt.dto.PromptResponse;
import com.gpt_hub.domain.prompt.entity.Prompt;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PromptMapper {

    PromptMapper INSTANCE = Mappers.getMapper(PromptMapper.class);

    PromptResponse promptToPromptResponse(Prompt prompt);
}
