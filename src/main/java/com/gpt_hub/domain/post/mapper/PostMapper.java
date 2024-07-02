package com.gpt_hub.domain.post.mapper;

import com.gpt_hub.domain.post.dto.PostResponse;
import com.gpt_hub.domain.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostResponse postToPostResponse(Post post, Long postId, Long userId, Long gptDataId);
}
