package com.gpt_hub.domain.prompt.entity;

import com.gpt_hub.common.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Prompt extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prompt_id")
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Lob
    @Column(nullable = false)
    private String body;

    private boolean isDeleted;

    public Prompt(Long userId, String body) {
        this.userId = userId;
        this.body = body;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
