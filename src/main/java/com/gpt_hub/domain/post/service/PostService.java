package com.gpt_hub.domain.post.service;

import com.gpt_hub.domain.gptdata.entity.GptData;
import com.gpt_hub.domain.gptdata.service.GptDataSearchService;
import com.gpt_hub.domain.post.dto.PostRequest;
import com.gpt_hub.domain.post.dto.PostResponse;
import com.gpt_hub.domain.post.entity.Post;
import com.gpt_hub.domain.post.mapper.PostMapper;
import com.gpt_hub.domain.post.repository.PostRepository;
import com.gpt_hub.domain.user.entity.User;
import com.gpt_hub.domain.user.service.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserSearchService userSearchService;
    private final GptDataSearchService gptDataSearchService;

    public PostResponse createPost(Long loginUserId, PostRequest postRequest) {
        User findUser = userSearchService.findById(loginUserId);

        GptData findGptData = null;
        if (postRequest.getGptDataId() != null) {
            findGptData = gptDataSearchService.findById(postRequest.getGptDataId());
        }

        Post newPost = new Post(
                findUser, findGptData, postRequest.getTitle(), postRequest.getBody(), postRequest.getForum()
        );

        Post savedPost = postRepository.save(newPost);

        return PostMapper.INSTANCE.postToPostResponse(
                savedPost, savedPost.getId(), loginUserId, postRequest.getGptDataId()
        );
    }
}
