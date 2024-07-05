package com.gpt_hub.domain.payment.entity;

import com.gpt_hub.common.base.BaseTimeEntity;
import com.gpt_hub.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTimeEntity {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int amount;

    private String details;

    private boolean isPaid;

    private boolean isRefunded;

    public Payment(String id, User user, int amount, String details) {
        this.id = id;
        this.user = user;
        this.amount = amount;
        this.details = details;
    }

    public void completePayment() {
        this.isPaid = true;
    }

    public void refundPayment() {
        this.isRefunded = true;
    }
}
