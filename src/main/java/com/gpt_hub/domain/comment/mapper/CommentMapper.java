package com.gpt_hub.domain.comment.mapper;

import com.gpt_hub.domain.comment.dto.CommentResponse;
import com.gpt_hub.domain.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentResponse commentToCommentResponse(Comment comment, Long commentId, Long userId, Long postId);
}
