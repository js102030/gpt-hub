package com.gpt_hub.domain.gptdata.service;

import com.gpt_hub.common.exception.custom.NotFoundException;
import com.gpt_hub.domain.gptdata.entity.GptData;
import com.gpt_hub.domain.gptdata.repository.GptDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GptDataSearchService {

    private final GptDataRepository gptDataRepository;

    public GptData findById(Long gptDataId) {
        return gptDataRepository.findById(gptDataId).orElseThrow(
                () -> new NotFoundException(String.format("GptDataId %d 에 해당하는 GptData 를 찾을 수 없습니다.", gptDataId)));
    }
}
