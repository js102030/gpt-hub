package com.gpt_hub.domain.user.repository;

import com.gpt_hub.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByLoginId(String loginId);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    Optional<User> findByLoginIdAndIsDeletedFalseAndIsBannedFalse(String loginId);

}
