package com.gpt_hub.domain.pointearn.event;

import com.gpt_hub.domain.pointearn.service.PointEarnService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CustomEventListener {

    private final PointEarnService pointEarnService;

    @Transactional
    @EventListener
    public void handleUserAction(UserActionEvent event) {
        pointEarnService.earnPoints(event.getUserId(), event.getActivityType());
    }
}
