package com.gpt_hub.domain.like.controller;

import static org.springframework.http.HttpStatus.OK;

import com.gpt_hub.common.annotation.LoginUserId;
import com.gpt_hub.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    @ResponseStatus(OK)
    @PostMapping("/posts/{postId}/likes")
    public void clickPostLike(@LoginUserId Long loginUserId, @PathVariable Long postId) {
        likeService.clickPostLike(loginUserId, postId);
    }

    @ResponseStatus(OK)
    @PostMapping("/comments/{commentId}/likes")
    public void clickCommentLike(@LoginUserId Long loginUserId, @PathVariable Long commentId) {
        likeService.clickCommentLike(loginUserId, commentId);
    }
}
