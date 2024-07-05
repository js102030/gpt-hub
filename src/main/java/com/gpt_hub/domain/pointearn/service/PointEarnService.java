package com.gpt_hub.domain.pointearn.service;

import static com.gpt_hub.domain.pointearn.enumtype.ActivityType.LOGIN;

import com.gpt_hub.domain.pointearn.entity.PointEarn;
import com.gpt_hub.domain.pointearn.enumtype.ActivityType;
import com.gpt_hub.domain.pointearn.repository.PointEarnRepository;
import com.gpt_hub.domain.pointpocket.entity.PointPocket;
import com.gpt_hub.domain.pointpocket.service.PointPocketService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PointEarnService {

    private final int POINTS_FOR_POST = 5;
    private final int POINTS_FOR_COMMENT = 3;
    private final int POINTS_FOR_LOGIN = 10;

    private final PointEarnRepository pointEarnRepository;
    private final PointPocketService pointPocketService;

    public void earnPoints(Long userId, ActivityType activityType) {
        PointPocket pointPocket = pointPocketService.getEarningPocketOrCreate(userId);

        if (activityType == LOGIN && pointEarnRepository.hasLoginEarnLast24Hours(userId)) {
            return;
        }

        int earnedAmount = calculatePoints(activityType);
        pointPocket.addPoints(BigDecimal.valueOf(earnedAmount));

        PointEarn newPointEarn = new PointEarn(userId, earnedAmount, activityType);
        pointEarnRepository.save(newPointEarn);
    }

    private int calculatePoints(ActivityType activityType) {
        return switch (activityType) {
            case POST -> POINTS_FOR_POST;
            case COMMENT -> POINTS_FOR_COMMENT;
            case LOGIN -> POINTS_FOR_LOGIN;
        };
    }
}
