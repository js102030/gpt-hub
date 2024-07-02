package com.gpt_hub.domain.prompt.repository;

import com.gpt_hub.domain.prompt.entity.Prompt;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromptRepository extends JpaRepository<Prompt, Long> {
    Optional<Prompt> findByIdAndUserId(Long promptId, Long userId);
}
