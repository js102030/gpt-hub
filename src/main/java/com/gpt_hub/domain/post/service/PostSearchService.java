package com.gpt_hub.domain.post.service;

import com.gpt_hub.common.exception.custom.NotFoundException;
import com.gpt_hub.domain.post.dto.PostResponse;
import com.gpt_hub.domain.post.entity.Post;
import com.gpt_hub.domain.post.mapper.PostMapper;
import com.gpt_hub.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostSearchService {

    private final PostRepository postRepository;

    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(String.format("PostId %d 에 해당하는 게시글을 찾을 수 없습니다.", postId)));
    }

    public Post findByIdAndUserId(Long postId, Long userId) {
        return postRepository.findByIdAndUserId(postId, userId)
                .orElseThrow(() -> new NotFoundException(String.format("PostId %d 에 해당하는 게시글을 찾을 수 없습니다.", postId)));
    }

    public PostResponse findPostResponseById_ADMIN(Long postId, Long loginUserId) {
        Post findPost = findById(postId);
        Long gptDataId = findPost.getGptData() == null ? null : findPost.getGptData().getId();
        return PostMapper.INSTANCE.postToPostResponse(
                findPost, findPost.getId(), loginUserId, gptDataId
        );
    }
}
