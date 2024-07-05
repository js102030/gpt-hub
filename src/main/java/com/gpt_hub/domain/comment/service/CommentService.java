package com.gpt_hub.domain.comment.service;

import static com.gpt_hub.domain.pointearn.enumtype.ActivityType.COMMENT;

import com.gpt_hub.domain.comment.dto.CommentResponse;
import com.gpt_hub.domain.comment.entity.Comment;
import com.gpt_hub.domain.comment.mapper.CommentMapper;
import com.gpt_hub.domain.comment.repository.CommentRepository;
import com.gpt_hub.domain.pointearn.event.UserActionEvent;
import com.gpt_hub.domain.post.entity.Post;
import com.gpt_hub.domain.post.service.PostSearchService;
import com.gpt_hub.domain.user.entity.User;
import com.gpt_hub.domain.user.service.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher eventPublisher;

    public CommentResponse createComment(Long loginUserId, Long postId, String commentBody) {
        User findUser = userSearchService.findById(loginUserId);
        Post findPost = postSearchService.findNotDeletedById(postId);

        Comment newComment = new Comment(findUser, findPost, commentBody);
        Comment savedComment = commentRepository.save(newComment);

        eventPublisher.publishEvent(new UserActionEvent(this, findUser.getId(), COMMENT));

        return CommentMapper.INSTANCE.commentToCommentResponse(
                savedComment, savedComment.getId(), loginUserId, postId
        );
    }

    public CommentResponse findCommentResponseById_ADMIN(Long commentId, Long loginUserId) {
        Comment findComment = commentSearchService.findByIdAndUserId(commentId, loginUserId);

        return CommentMapper.INSTANCE.commentToCommentResponse(
                findComment, findComment.getId(), loginUserId, findComment.getPost().getId());
    }

    public CommentResponse updateComment(Long loginUserId, Long commentId, String commentBody) {
        Comment findComment = commentSearchService.findByIdAndUserId(commentId, loginUserId);

        findComment.updateComment(commentBody);

        return CommentMapper.INSTANCE.commentToCommentResponse(
                findComment, findComment.getId(), loginUserId, findComment.getPost().getId());
    }

    public void deleteComment(Long loginUserId, Long commentId) {
        Comment findComment = commentSearchService.findByIdAndUserId(commentId, loginUserId);

        findComment.delete();
    }
}
