package com.gpt_hub.domain.post.controller;

import com.gpt_hub.common.annotation.LoginUserId;
import com.gpt_hub.domain.post.dto.PostRequest;
import com.gpt_hub.domain.post.dto.PostResponse;
import com.gpt_hub.domain.post.service.PostSearchService;
import com.gpt_hub.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;
    private final PostSearchService postSearchService;

    @PostMapping("/posts")
    public PostResponse writePost(@LoginUserId Long loginUserId, @RequestBody @Valid PostRequest postRequest) {
        return postService.createPost(loginUserId, postRequest);
    }
}
