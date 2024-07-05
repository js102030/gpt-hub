package com.gpt_hub.domain.payment.repository;

import com.gpt_hub.domain.payment.entity.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByIdAndUserId(String paymentId, Long userId);

    Optional<Payment> findTopByUserIdAndIsPaidFalseOrderByCreatedDateDesc(Long userId);
}
