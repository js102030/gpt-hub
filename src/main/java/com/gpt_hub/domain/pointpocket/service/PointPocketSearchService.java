package com.gpt_hub.domain.pointpocket.service;

import com.gpt_hub.domain.pointpocket.PointPocket;
import com.gpt_hub.domain.pointpocket.repository.PointPocketRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointPocketSearchService {

    private final PointPocketRepository pointPocketRepository;

    public Optional<PointPocket> findEarningPocketOptional(Long userId) {
        return pointPocketRepository.findByUserIdAndPaymentIdIsNull(userId);
    }
}
