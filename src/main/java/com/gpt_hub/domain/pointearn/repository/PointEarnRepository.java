package com.gpt_hub.domain.pointearn.repository;

import com.gpt_hub.domain.pointearn.entity.PointEarn;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointEarnRepository extends JpaRepository<PointEarn, Long> {

    @Query("SELECT COUNT(pe) FROM PointEarn pe " +
            "WHERE pe.userId = :userId " +
            "AND pe.activityType = 'LOGIN' " +
            "AND pe.createdDate >= :yesterday")
    long countLoginEarnLast24Hours(Long userId, LocalDateTime yesterday);

    default boolean hasLoginEarnLast24Hours(Long userId) {
        LocalDateTime yesterday = LocalDateTime.now().minusHours(24);
        return countLoginEarnLast24Hours(userId, yesterday) > 0;
    }

}
