package com.gpt_hub.domain.like.service;

import com.gpt_hub.domain.like.entity.Like;
import com.gpt_hub.domain.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final LikeSearchService likeSearchService;

    public void clickPostLike(Long loginUserId, Long postId) {
        Like findLike = likeSearchService.findByUserIdAndPostId(loginUserId, postId);

        if (findLike != null) {
            likeRepository.delete(findLike);
            return;
        }
        Like newLike = Like.ofPost(loginUserId, postId);
        likeRepository.save(newLike);
    }

    public void clickCommentLike(Long loginUserId, Long commentId) {
        Like findLike = likeSearchService.findByUserIdAndCommentId(loginUserId, commentId);

        if (findLike != null) {
            likeRepository.delete(findLike);
            return;
        }
        Like newLike = Like.ofComment(loginUserId, commentId);
        likeRepository.save(newLike);
    }
}
