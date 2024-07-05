package com.gpt_hub.domain.user.controller.api;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import com.gpt_hub.common.annotation.LoginUserId;
import com.gpt_hub.domain.user.dto.SignUpRequest;
import com.gpt_hub.domain.user.dto.UpdateNicknameDto;
import com.gpt_hub.domain.user.dto.UpdatePasswordRequest;
import com.gpt_hub.domain.user.dto.UserResponse;
import com.gpt_hub.domain.user.service.UserSearchService;
import com.gpt_hub.domain.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UserSearchService userSearchService;

    @ResponseStatus(CREATED)
    @PostMapping("/users")
    public UserResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return userService.signUp(signUpRequest);
    }

    @ResponseStatus(OK)
    @GetMapping("/users/{userId}")
    public UserResponse findUser(@PathVariable Long userId) {
        return userSearchService.findUserResponse(userId);
    }

    @ResponseStatus(OK)
    @PatchMapping("/users/nickname")
    public UserResponse updateNickname(@LoginUserId Long loginUserId,
                                       @Valid @RequestBody UpdateNicknameDto request) {
        return userService.updateNickname(loginUserId, request.getNickname());
    }

    @ResponseStatus(OK)
    @PatchMapping("/users/password")
    public void updatePassword(@LoginUserId Long loginUserId,
                               @Valid @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(loginUserId, request.getPassword());
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/users")
    public void deleteUser(@LoginUserId Long loginUserId) {
        userService.delete(loginUserId);
    }

    @ResponseStatus(OK)
    @GetMapping(value = "/loginId-availability")
    public boolean checkLoginIdAvailability(@RequestParam @NotBlank String loginId) {
        return userSearchService.existsByLoginId(loginId);
    }

    @ResponseStatus(OK)
    @GetMapping(value = "/nickname-availability")
    public boolean checkNicknameAvailability(@RequestParam @NotBlank String nickname) {
        return userSearchService.existsByNickName(nickname);
    }

    @ResponseStatus(OK)
    @GetMapping("/email-availability")
    public boolean checkEmailAvailability(@RequestParam @NotBlank String email) {
        return userSearchService.existsByEmail(email);
    }
}
