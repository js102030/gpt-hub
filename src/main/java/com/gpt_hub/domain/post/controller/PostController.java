package com.gpt_hub.domain.post.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.gpt_hub.common.annotation.LoginUserId;
import com.gpt_hub.domain.post.dto.PostRequest;
import com.gpt_hub.domain.post.dto.PostResponse;
import com.gpt_hub.domain.post.service.PostSearchService;
import com.gpt_hub.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;
    private final PostSearchService postSearchService;

    @ResponseStatus(CREATED)
    @PostMapping("/posts")
    public PostResponse writePost(@LoginUserId Long loginUserId, @RequestBody @Valid PostRequest postRequest) {
        return postService.createPost(loginUserId, postRequest);
    }

    @ResponseStatus(OK)
    @PatchMapping("/posts/{postId}")
    public PostResponse updatePost(@LoginUserId Long loginUserId, @PathVariable Long postId,
                                   @RequestBody @Valid PostRequest postRequest) {
        return postService.updatePost(loginUserId, postId, postRequest);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/posts/{postId}")
    public void deletePost(@LoginUserId Long loginUserId, @PathVariable Long postId) {
        postService.deletePost(loginUserId, postId);
    }

    @ResponseStatus(OK)
    @GetMapping("/admin/posts/{postId}") //삭제된 게시글도 조회.
    public PostResponse adminGetPost(@LoginUserId Long loginUserId, @PathVariable Long postId) {
        return postSearchService.findPostResponseById_ADMIN(postId, loginUserId);
    }

}
