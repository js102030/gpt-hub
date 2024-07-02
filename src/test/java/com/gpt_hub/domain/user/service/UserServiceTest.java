package com.gpt_hub.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.gpt_hub.domain.user.dto.SignUpRequest;
import com.gpt_hub.domain.user.dto.UserResponse;
import com.gpt_hub.domain.user.entity.User;
import com.gpt_hub.domain.user.enumtype.Role;
import com.gpt_hub.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserSearchService userSearchService;
    @Mock
    UserRepository userRepository;
    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Test
    @DisplayName("성공 - 회원가입 테스트")
    void signUp() {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .loginId("아이디")
                .password("비밀번호")
                .nickname("닉네임")
                .build();

        UserResponse expectedUserResponse = UserResponse.builder()
                .id(1L)
                .loginId(signUpRequest.getLoginId())
                .role(Role.USER)
                .nickname(signUpRequest.getNickname())
                .email(null)
                .point(0)
                .isDeleted(false)
                .isVerified(false)
                .isBanned(false)
                .build();

        User expectedUser = new User("아이디", "암호화된비밀번호", "닉네임");

        when(userSearchService.existsByLoginId(signUpRequest.getLoginId())).thenReturn(false);
        when(userSearchService.existsByNickName(signUpRequest.getNickname())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(
                expectedUser
        );
        when(passwordEncoder.encode(anyString())).thenReturn("암호화된비밀번호");
        when(userSearchService.findById(any())).thenReturn(expectedUser);

        UserResponse userResponse = userService.signUp(signUpRequest);

        User findUser = userSearchService.findById(expectedUserResponse.getId());

        assertThat(findUser.getPassword()).isEqualTo(expectedUser.getPassword());
        assertThat(findUser.getId()).isEqualTo(userResponse.getId());
        assertThat(findUser.getNickname()).isEqualTo(userResponse.getNickname());
        assertThat(findUser.getLoginId()).isEqualTo(userResponse.getLoginId());
    }

    @Test
    @DisplayName("실패 - 회원가입 테스트 - 이미 존재하는 회원")
    void signUpWithExistingLoginId() {
        // given
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .loginId("existingId")
                .password("비밀번호")
                .nickname("닉네임")
                .build();

        // when
        when(userSearchService.existsByLoginId(signUpRequest.getLoginId())).thenReturn(true);

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.signUp(signUpRequest);
        });
    }

    @Test
    @DisplayName("실패 - 회원가입 테스트 - 이미 존재하는 닉네임")
    void signUpWithExistingNickname() {
        // given
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .loginId("아이디")
                .password("비밀번호")
                .nickname("existingNickname")
                .build();

        // when
        when(userSearchService.existsByNickName(signUpRequest.getNickname())).thenReturn(true);

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.signUp(signUpRequest);
        });
    }
}