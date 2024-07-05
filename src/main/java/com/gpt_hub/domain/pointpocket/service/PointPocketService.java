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

    public PointPocket getEarningPocketOrCreate(Long userId) {
        return pointPocketRepository.findByUserIdAndPaymentIdIsNull(userId).orElseGet(
                () -> {
                    PointPocket pointPocket = createEarningPointPocket(userId);
                    return pointPocketRepository.save(pointPocket);
                }
        );
    }

    public PointPocket getEarningPocketOrCreate_LOCK(Long userId) {
        return pointPocketRepository.findByUserIdAndPaymentIdIsNull_LOCK(userId).orElseGet(
                () -> {
                    PointPocket pointPocket = createEarningPointPocket(userId);
                    pointPocketRepository.save(pointPocket);
                    return pointPocketRepository.findByUserIdAndPaymentIdIsNull_LOCK(userId).get();
                });
    }

    private PointPocket createEarningPointPocket(Long userId) { // private
        PointPocket newPointPocket = PointPocket.ofEarn(userId);
        return pointPocketRepository.save(newPointPocket);
    }

    public PointPocket createPaymentPointPocket(Long userId, String paymentId) { // public
        PointPocket newPointPocket = PointPocket.ofPayment(userId, paymentId);
        return pointPocketRepository.save(newPointPocket);
    }
}
