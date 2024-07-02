package com.gpt_hub.domain.like.repository;

import com.gpt_hub.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Like findByUserIdAndPostId(Long userId, Long postId);

    Like findByUserIdAndCommentId(Long userId, Long commentId);

}
