package com.gpt_hub.domain.user.service;

import com.gpt_hub.domain.user.dto.SignUpRequest;
import com.gpt_hub.domain.user.dto.UserResponse;
import com.gpt_hub.domain.user.entity.User;
import com.gpt_hub.domain.user.mapper.UserMapper;
import com.gpt_hub.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserSearchService userSearchService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponse signUp(SignUpRequest signUpRequest) {
        if (userSearchService.existsByLoginId(signUpRequest.getLoginId())) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
        if (userSearchService.existsByNickName(signUpRequest.getNickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        User user = UserMapper.INSTANCE.signUpRequestToUser(signUpRequest,
                passwordEncoder.encode(signUpRequest.getPassword()));

        User savedUser = userRepository.save(user);

        return UserMapper.INSTANCE.userToUserResponse(savedUser);
    }

    public UserResponse updateNickname(Long userId, String nickname) {
        User findUser = userSearchService.findById(userId);

        findUser.updateNickname(nickname);

        return UserMapper.INSTANCE.userToUserResponse(findUser);
    }

    public void updatePassword(Long userId, String password) {
        User findUser = userSearchService.findById(userId);

        findUser.updatePassword(passwordEncoder.encode(password));
    }

    public void delete(Long userId) {
        User findUser = userSearchService.findById(userId);

        findUser.delete();
    }
}
