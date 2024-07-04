package com.gpt_hub.domain.pointpocket.service;

import com.gpt_hub.domain.pointpocket.entity.PointPocket;
import com.gpt_hub.domain.pointpocket.repository.PointPocketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PointPocketService {

    private final PointPocketRepository pointPocketRepository;

    public PointPocket createEarningPointPocket(Long userId) {
        PointPocket newPointPocket = PointPocket.ofEarn(userId);
        return pointPocketRepository.save(newPointPocket);
    }

    public PointPocket createPaymentPointPocket(Long userId, String paymentId) {
        PointPocket newPointPocket = PointPocket.ofPayment(userId, paymentId);
        return pointPocketRepository.save(newPointPocket);
    }
}
