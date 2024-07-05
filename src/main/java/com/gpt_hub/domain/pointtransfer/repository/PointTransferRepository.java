package com.gpt_hub.domain.pointtransfer.repository;

import com.gpt_hub.domain.pointtransfer.entity.PointTransfer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTransferRepository extends JpaRepository<PointTransfer, Long> {
    List<PointTransfer> findAllBySenderIdAndReceiverId(Long loginUserId, Long loginUserId1);
}
