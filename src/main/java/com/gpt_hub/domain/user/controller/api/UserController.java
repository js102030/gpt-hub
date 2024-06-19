package com.gpt_hub.domain.user.controller.api;

import static org.springframework.http.HttpStatus.CREATED;

import com.gpt_hub.domain.user.dto.SignUpRequest;
import com.gpt_hub.domain.user.dto.UpdateNicknameDto;
import com.gpt_hub.domain.user.dto.UpdatePasswordRequest;
import com.gpt_hub.domain.user.dto.UserResponse;
import com.gpt_hub.domain.user.service.UserSearchService;
import com.gpt_hub.domain.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UserSearchService userSearchService;

    @PostMapping("/users")
    public ResponseEntity<UserResponse> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        UserResponse userResponse = userService.signUp(signUpRequest);

        return ResponseEntity
                .status(CREATED)
                .body(userResponse);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> findUser(@PathVariable Long userId) {
        UserResponse userResponse = userSearchService.findUserResponse(userId);

        return ResponseEntity
                .ok(userResponse);
    }

    @PatchMapping("/users/nickname")
    public ResponseEntity<UserResponse> updateNickname(@AuthenticationPrincipal(expression = "userId") Long userId,
                                                       @Valid @RequestBody UpdateNicknameDto request) {
        UserResponse userResponse = userService.updateNickname(userId, request.getNickname());

        return ResponseEntity
                .ok(userResponse);
    }

    @PatchMapping("/users/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal(expression = "userId") Long userId,
                                               @Valid @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(userId, request.getPassword());

        return ResponseEntity
                .ok()
                .build();
    }

    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal(expression = "userId") Long userId) {
        userService.delete(userId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(value = "/loginId-availability")
    public ResponseEntity<Boolean> checkLoginIdAvailability(@RequestParam @NotBlank String loginId) {
        return ResponseEntity
                .ok(userSearchService.existsByLoginId(loginId));
    }

    @GetMapping(value = "/nickname-availability")
    public ResponseEntity<Boolean> checkNicknameAvailability(@RequestParam @NotBlank String nickname) {
        return ResponseEntity
                .ok(userSearchService.existsByNickName(nickname));
    }

    @GetMapping("/email-availability")
    public ResponseEntity<Boolean> checkEmailAvailability(@RequestParam @NotBlank String email) {
        return ResponseEntity
                .ok(userSearchService.existsByEmail(email));
    }
}
