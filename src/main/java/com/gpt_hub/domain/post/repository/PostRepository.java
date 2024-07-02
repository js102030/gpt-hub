package com.gpt_hub.domain.post.repository;

import com.gpt_hub.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
