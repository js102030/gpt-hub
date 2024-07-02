package com.gpt_hub.domain.like.service;

import com.gpt_hub.domain.like.entity.Like;
import com.gpt_hub.domain.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeSearchService {

    private final LikeRepository likeRepository;

    public Like findByUserIdAndPostId(Long userId, Long postId) {
        return likeRepository.findByUserIdAndPostId(userId, postId);
    }

    public Like findByUserIdAndCommentId(Long userId, Long postId) {
        return likeRepository.findByUserIdAndCommentId(userId, postId);
    }
}
