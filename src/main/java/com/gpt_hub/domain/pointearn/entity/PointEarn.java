package com.gpt_hub.domain.pointearn.entity;

import com.gpt_hub.common.base.BaseTimeEntity;
import com.gpt_hub.domain.pointearn.enumtype.ActivityType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PointEarn extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_earn_id")
    private Long id;

    private Long userId;

    private int amount;

    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    public PointEarn(Long userId, int amount, ActivityType activityType) {
        this.userId = userId;
        this.amount = amount;
        this.activityType = activityType;
    }
}
