package com.gpt_hub.domain.comment.service;

import com.gpt_hub.domain.comment.dto.CommentRequest;
import com.gpt_hub.domain.comment.dto.CommentResponse;
import com.gpt_hub.domain.comment.entity.Comment;
import com.gpt_hub.domain.comment.mapper.CommentMapper;
import com.gpt_hub.domain.comment.repository.CommentRepository;
import com.gpt_hub.domain.post.entity.Post;
import com.gpt_hub.domain.post.service.PostSearchService;
import com.gpt_hub.domain.user.entity.User;
import com.gpt_hub.domain.user.service.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentSearchService commentSearchService;
    private final UserSearchService userSearchService;
    private final PostSearchService postSearchService;

    public CommentResponse createComment(Long loginUserId, Long postId, CommentRequest commentRequest) {
        User findUser = userSearchService.findById(loginUserId);
        Post findPost = postSearchService.findNotDeletedById(postId);

        Comment newComment = new Comment(findUser, findPost, commentRequest.getBody());
        Comment savedComment = commentRepository.save(newComment);

        return CommentMapper.INSTANCE.commentToCommentResponse(
                savedComment, savedComment.getId(), loginUserId, postId
        );
    }

    public CommentResponse findCommentResponseById_ADMIN(Long commentId, Long loginUserId) {
        Comment findComment = commentSearchService.findByIdAndUserId(commentId, loginUserId);

        return CommentMapper.INSTANCE.commentToCommentResponse(
                findComment, findComment.getId(), loginUserId, findComment.getPost().getId());
    }
}
