package com.gpt_hub.domain.post.service;

import com.gpt_hub.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostSearchService {

    private final PostRepository postRepository;
}
