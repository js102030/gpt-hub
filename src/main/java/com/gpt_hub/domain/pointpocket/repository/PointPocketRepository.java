package com.gpt_hub.domain.pointpocket.repository;

import com.gpt_hub.domain.pointpocket.PointPocket;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointPocketRepository extends JpaRepository<PointPocket, Long> {

    Optional<PointPocket> findByUserIdAndPaymentIdIsNull(Long userId);
}
