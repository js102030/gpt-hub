package com.gpt_hub.domain.pointpocket.service;

import com.gpt_hub.common.exception.custom.NotFoundException;
import com.gpt_hub.domain.pointpocket.dto.PointPocketResponse;
import com.gpt_hub.domain.pointpocket.entity.PointPocket;
import com.gpt_hub.domain.pointpocket.mapper.PointPocketMapper;
import com.gpt_hub.domain.pointpocket.repository.PointPocketRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointPocketSearchService {

    private final PointPocketRepository pointPocketRepository;

    public List<PointPocket> findPaymentPointPocketsByUserId(Long userId) {
        return pointPocketRepository.findValidPaymentPointPockets(userId);
    }

    public Optional<PointPocket> getEarningPocket(Long userId) {
        return pointPocketRepository.findByUserIdAndPaymentIdIsNull(userId);

    }

    public BigDecimal findTotalPointsByUserId(Long userId) {
        return pointPocketRepository.findTotalPointsByUserId(userId);
    }

    public List<PointPocketResponse> findAllPointPocketsOrNull(Long userId) {
        List<PointPocket> findPointPockets = pointPocketRepository.findAllByUserId(userId);

        return findPointPockets.stream()
                .map(PointPocketMapper.INSTANCE::toPointPocketResponse)
                .toList();
    }

    public PointPocket findPointPocketByPaymentId(String paymentId) {
        return pointPocketRepository.findByPaymentId(paymentId).orElseThrow(
                () -> new NotFoundException(String.format("결제 ID가 %s인 포인트 지갑이 존재하지 않습니다.", paymentId)));
    }
}
