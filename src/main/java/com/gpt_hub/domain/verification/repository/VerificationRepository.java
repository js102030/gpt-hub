package com.gpt_hub.domain.verification.repository;

import com.gpt_hub.domain.verification.entity.Verification;
import java.util.Date;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository extends JpaRepository<Verification, Long> {

    Optional<Verification> findByVerificationCodeAndUserId(String verificationCode, Long userId);

    void deleteAllByExpiryDateBefore(Date now);
}
