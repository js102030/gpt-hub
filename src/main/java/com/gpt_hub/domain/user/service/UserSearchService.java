package com.gpt_hub.domain.user.service;

import com.gpt_hub.common.exception.custom.NotFoundException;
import com.gpt_hub.domain.user.dto.UserResponse;
import com.gpt_hub.domain.user.entity.User;
import com.gpt_hub.domain.user.mapper.UserMapper;
import com.gpt_hub.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
                        () -> new NotFoundException(String.format("유저 아이디 %d 에 해당하는 유저를 찾을 수 없습니다.", userId))
                );
    }

    public User findByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(
                        () -> new NotFoundException(String.format("로그인 아이디 %s 에 해당하는 유저를 찾을 수 없습니다.", loginId))
                );
    }

    public UserResponse findUserResponse(Long userId) {
        return userRepository.findById(userId)
                .map(UserMapper.INSTANCE::userToUserResponse)
                .orElseThrow(
                        () -> new NotFoundException(String.format("유저 아이디 %d 에 해당하는 유저를 찾을 수 없습니다.", userId))
                );
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
