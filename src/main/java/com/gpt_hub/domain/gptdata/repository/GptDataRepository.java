package com.gpt_hub.domain.gptdata.repository;

import com.gpt_hub.domain.gptdata.entity.GptData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GptDataRepository extends JpaRepository<GptData, Long> {

}
