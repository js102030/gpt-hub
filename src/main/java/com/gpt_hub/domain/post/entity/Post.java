package com.gpt_hub.domain.post.entity;

import com.gpt_hub.common.base.BaseTimeEntity;
import com.gpt_hub.domain.gptdata.entity.GptData;
import com.gpt_hub.domain.post.enumtype.Forum;
import com.gpt_hub.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gpt_data_id")
    private GptData gptData;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Forum forum;

    private int hits;

    private boolean isDeleted;

    public Post(User user, GptData gptData, String title, String body, Forum forum) {
        this.user = user;
        this.gptData = gptData;
        this.title = title;
        this.body = body;
        this.forum = forum;
    }
}
