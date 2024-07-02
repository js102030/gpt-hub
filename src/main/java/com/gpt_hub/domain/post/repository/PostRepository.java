package com.gpt_hub.domain.post.repository;

import com.gpt_hub.domain.post.entity.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndUserId(Long postId, Long userId);

    Optional<Post> findByIdAndIsDeletedFalse(Long postId);
}
