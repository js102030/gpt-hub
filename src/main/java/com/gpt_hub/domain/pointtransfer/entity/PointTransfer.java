package com.gpt_hub.domain.pointtransfer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PointTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_transfer_id")
    private Long id;

    @Column(nullable = false)
    private Long senderId;

    @Column(nullable = false)
    private Long receiverId;

    @Column(nullable = false)
    private int amount;

    private String message;

    public static PointTransfer ofTransfer(Long senderId, Long receiverId, int amount, String message) {
        PointTransfer pointTransfer = new PointTransfer();
        pointTransfer.senderId = senderId;
        pointTransfer.receiverId = receiverId;
        pointTransfer.amount = amount;
        pointTransfer.message = message;
        return pointTransfer;
    }

    public static PointTransfer ofGpt(Long senderId, int amount, String message) {
        PointTransfer pointTransfer = new PointTransfer();
        pointTransfer.senderId = senderId;
        pointTransfer.amount = amount;
        pointTransfer.message = message;
        return pointTransfer;
    }

}
