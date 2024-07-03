package com.gpt_hub.domain.payment.service;

import com.gpt_hub.common.exception.custom.NotFoundException;
import com.gpt_hub.domain.payment.entity.Payment;
import com.gpt_hub.domain.payment.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class PaymentSearchService {

    private PaymentRepository paymentRepository;

    public Payment findByIdAndUserId(Long paymentId, Long userId) {
        return paymentRepository.findByIdAndUserId(paymentId, userId)
                .orElseThrow(() -> new NotFoundException("결제 정보가 없습니다."));
    }

    public Payment findRecentUnpaidPaymentByUserId(Long userId) {
        return paymentRepository.findTopByUserIdAndIsPaidFalseOrderByCreatedDateDesc(userId)
                .orElseThrow(() -> new NotFoundException("결제 정보가 없습니다."));
    }
}
