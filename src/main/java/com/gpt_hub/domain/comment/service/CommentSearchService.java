package com.gpt_hub.domain.comment.service;

import com.gpt_hub.common.exception.custom.NotFoundException;
import com.gpt_hub.domain.comment.entity.Comment;
import com.gpt_hub.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentSearchService {

    private final CommentRepository commentRepository;

    public Comment findByIdAndUserId(Long commentId, Long userId) {
        return commentRepository.findByIdAndUserId(commentId, userId)
                .orElseThrow(
                        () -> new NotFoundException(String.format("CommentId %d 에 해당하는 게시글을 찾을 수 없습니다.", commentId))
                );
    }
}


