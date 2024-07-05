package com.gpt_hub.domain.pointtransfer.service;

import com.gpt_hub.common.exception.custom.NotEnoughPointException;
import com.gpt_hub.domain.pointpocket.entity.PointPocket;
import com.gpt_hub.domain.pointpocket.service.PointPocketService;
import com.gpt_hub.domain.pointtransfer.dto.PointTransferRequest;
import com.gpt_hub.domain.pointtransfer.dto.PointTransferResponse;
import com.gpt_hub.domain.pointtransfer.entity.PointTransfer;
import com.gpt_hub.domain.pointtransfer.mapper.PointTransferMapper;
import com.gpt_hub.domain.pointtransfer.repository.PointTransferRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PointTransferService {

    private final PointTransferRepository pointTransferRepository;
    private final PointPocketService pointPocketService;

    public PointTransferResponse transferPoints(Long loginUserId, PointTransferRequest request) {
        Long receiverId = request.getReceiverId();

        PointPocket senderPocket = pointPocketService.getEarningPocketOrCreate_LOCK(loginUserId);
        PointPocket receiverPocket = pointPocketService.getEarningPocketOrCreate_LOCK(receiverId);

        BigDecimal amountBigDecimal = BigDecimal.valueOf(request.getAmount());

        if (senderPocket.getPoints().compareTo(amountBigDecimal) < 0) {
            throw new NotEnoughPointException(
                    String.format("적립 포인트가 부족합니다. (현재 보유 포인트: %s)", senderPocket.getPoints())
            );
        }

        senderPocket.addPoints(amountBigDecimal.negate());
        receiverPocket.addPoints(amountBigDecimal);

        PointTransfer newPointTransfer =
                PointTransfer.ofTransfer(loginUserId, receiverId, request.getAmount(), request.getMessage());

        PointTransfer savedPointTransfer = pointTransferRepository.save(newPointTransfer);

        return PointTransferMapper.INSTANCE.toPointTransferResponse(savedPointTransfer);
    }
}
