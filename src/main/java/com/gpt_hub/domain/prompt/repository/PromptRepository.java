package com.gpt_hub.domain.prompt.repository;

import com.gpt_hub.domain.prompt.entity.Prompt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromptRepository extends JpaRepository<Prompt, Long> {
}
