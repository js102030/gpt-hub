package com.gpt_hub.domain.user.service;

import com.gpt_hub.common.exception.custom.NotFoundException;
import com.gpt_hub.domain.user.dto.UserResponse;
import com.gpt_hub.domain.user.entity.User;
import com.gpt_hub.domain.user.mapper.UserMapper;
import com.gpt_hub.domain.user.repository.UserRepository;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserSearchService {

    private final UserRepository userRepository;

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(
                        () -> new NotFoundException(String.format("UserId %d 에 해당하는 유저를 찾을 수 없습니다.", userId))
                );
    }

    @Lock(LockModeType.PESSIMISTIC_READ)
    public User findByIdWithLock(Long userId) {
        return findById(userId);
    }

    public User findByLoginId(String loginId) {
        return userRepository.findByLoginIdAndIsDeletedFalseAndIsBannedFalse(loginId)
                .orElseThrow(
                        () -> new NotFoundException(String.format("LoginId %s 에 해당하는 유저를 찾을 수 없습니다.", loginId))
                );
    }

    public UserResponse findUserResponse(Long userId) {
        return UserMapper.INSTANCE.userToUserResponse(findById(userId));
    }

    public boolean existsByLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    public boolean existsByNickName(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
