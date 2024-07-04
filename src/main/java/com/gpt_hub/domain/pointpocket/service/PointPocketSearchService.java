package com.gpt_hub.domain.pointpocket.service;

import com.gpt_hub.domain.pointpocket.dto.PointPocketResponse;
import com.gpt_hub.domain.pointpocket.entity.PointPocket;
import com.gpt_hub.domain.pointpocket.mapper.PointPocketMapper;
import com.gpt_hub.domain.pointpocket.repository.PointPocketRepository;
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

    public Optional<PointPocket> findEarningPocketOptional(Long userId) {
        return pointPocketRepository.findByUserIdAndPaymentIdIsNull(userId);
    }

    public List<PointPocketResponse> findAllPointPocketsOrNull(Long loginUserId) {
        List<PointPocket> findPointPockets = pointPocketRepository.findAllByUserId(loginUserId);
     
        return findPointPockets.stream()
                .map(PointPocketMapper.INSTANCE::toPointPocketResponse)
                .toList();
    }
}
