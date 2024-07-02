package com.gpt_hub.domain.comment.controller;

import com.gpt_hub.common.annotation.LoginUserId;
import com.gpt_hub.domain.comment.dto.CommentRequest;
import com.gpt_hub.domain.comment.dto.CommentResponse;
import com.gpt_hub.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public CommentResponse writeComment(@LoginUserId Long loginUserId,
                                        @RequestBody @Valid CommentRequest commentRequest,
                                        @PathVariable Long postId) {
        return commentService.createComment(loginUserId, postId, commentRequest);
    }

    @GetMapping("/admin/comments/{commentId}")
    public CommentResponse adminGetComment(@LoginUserId Long loginUserId, @PathVariable Long commentId) {
        return commentService.findCommentResponseById_ADMIN(commentId, loginUserId);
    }
}
