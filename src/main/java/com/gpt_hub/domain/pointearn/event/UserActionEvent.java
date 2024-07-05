package com.gpt_hub.domain.pointearn.event;

import com.gpt_hub.domain.pointearn.enumtype.ActivityType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserActionEvent extends ApplicationEvent {

    private final Long userId;
    private final ActivityType activityType;

    public UserActionEvent(Object source, Long userId, ActivityType activityType) {
        super(source);
        this.userId = userId;
        this.activityType = activityType;
    }
}
