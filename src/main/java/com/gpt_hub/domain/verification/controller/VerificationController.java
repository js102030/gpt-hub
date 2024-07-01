package com.gpt_hub.domain.verification.controller;

import static org.springframework.http.HttpStatus.OK;

import com.gpt_hub.domain.user.annotation.LoginUserId;
import com.gpt_hub.domain.verification.dto.VerifyRequest;
import com.gpt_hub.domain.verification.service.VerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class VerificationController {

    private final VerificationService verificationService;

    @ResponseStatus(OK)
    @PostMapping("/verification")
    public String sendVerificationEmail(@LoginUserId Long loginUserId,
                                        @RequestParam("email") String email) throws Exception {
        final String code = verificationService.sendVerificationCode(loginUserId, email);

        log.info("인증코드 : {}", code);

        return code;
    }

    @ResponseStatus(OK)
    @PatchMapping("/verification/check")
    public void validateCodeAndActivateEmail(@LoginUserId Long loginUserId,
                                             @RequestBody VerifyRequest verifyRequest) {
        verificationService.activateEmailVerification(loginUserId, verifyRequest);
    }

}
