package com.gpt_hub.domain.pointpocket.repository;

import com.gpt_hub.domain.pointpocket.entity.PointPocket;
import jakarta.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface PointPocketRepository extends JpaRepository<PointPocket, Long> {

    Optional<PointPocket> findByUserIdAndPaymentIdIsNull(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT pp FROM PointPocket pp WHERE pp.userId = :userId AND pp.paymentId IS NULL")
    Optional<PointPocket> findByUserIdAndPaymentIdIsNull_LOCK(Long userId);

    @Query("SELECT p FROM PointPocket p"
            + " WHERE p.userId = :userId"
            + " AND p.paymentId IS NOT NULL"
            + " AND p.points > 0"
            + " ORDER BY p.createdDate ASC")
    List<PointPocket> findValidPaymentPointPockets(Long userId);

    List<PointPocket> findAllByUserId(Long loginUserId);

    @Query("SELECT COALESCE(SUM(pp.points), 0) FROM PointPocket pp WHERE pp.userId = :userId")
    BigDecimal findTotalPointsByUserId(Long userId);

    Optional<PointPocket> findByPaymentId(String paymentId);
}
