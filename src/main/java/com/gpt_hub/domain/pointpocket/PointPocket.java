package com.gpt_hub.domain.pointpocket;

import com.gpt_hub.common.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointPocket extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_pocket_id")
    private Long id;

    @Column(nullable = false)
    private int points = 0;

    @Column(nullable = false)
    private Long userId;

    private Long paymentId; //paymentId가 null 이라면 적립된 포인트. (적립된 포인트만 전송 가능)

    public static PointPocket ofPayment(Long userId, Long paymentId) {
        PointPocket pointPocket = new PointPocket();
        pointPocket.userId = userId;
        pointPocket.paymentId = paymentId;
        return pointPocket;
    }

    public static PointPocket ofEarn(Long userId) {
        PointPocket pointPocket = new PointPocket();
        pointPocket.userId = userId;
        return pointPocket;
    }

    public void addPoints(int amount) {
        this.points += amount;
    }
}
