package com.gpt_hub.domain.pointtransfer.service;

import com.gpt_hub.domain.pointtransfer.dto.PointTransferResponse;
import com.gpt_hub.domain.pointtransfer.mapper.PointTransferMapper;
import com.gpt_hub.domain.pointtransfer.repository.PointTransferRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointTransferSearchService {

    private final PointTransferRepository pointTransferRepository;

    public List<PointTransferResponse> getAllPointTransfers(Long loginUserId) {
        return pointTransferRepository.findAllBySenderIdAndReceiverId(loginUserId, loginUserId).stream()
                .map(PointTransferMapper.INSTANCE::toPointTransferResponse)
                .toList();
    }
}
