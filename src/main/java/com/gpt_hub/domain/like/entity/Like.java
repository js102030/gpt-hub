package com.gpt_hub.domain.like.entity;

import com.gpt_hub.common.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long commentId;

    public static Like ofComment(Long userId, Long commentId) {
        Like like = new Like();
        like.userId = userId;
        like.commentId = commentId;
        return like;
    }

    public static Like ofPost(Long userId, Long postId) {
        Like like = new Like();
        like.userId = userId;
        like.postId = postId;
        return like;
    }
}
