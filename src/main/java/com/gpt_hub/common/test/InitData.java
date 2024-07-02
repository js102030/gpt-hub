package com.gpt_hub.common.test;

import com.gpt_hub.domain.user.dto.SignUpRequest;
import com.gpt_hub.domain.user.dto.UserResponse;
import com.gpt_hub.domain.user.entity.User;
import com.gpt_hub.domain.user.service.UserSearchService;
import com.gpt_hub.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Component
public class InitData {

    private final UserService userService;
    private final UserSearchService userSearchService;
    private final BCryptPasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        log.info("init()");

        SignUpRequest signUpRequest1 = SignUpRequest.builder()
                .loginId("test1")
                .password("test1")
                .nickname("test1")
                .build();

        SignUpRequest signUpRequest2 = SignUpRequest.builder()
                .loginId("test2")
                .password("test2")
                .nickname("test2")
                .build();

        UserResponse userResponse1 = userService.signUp(signUpRequest1);
        UserResponse userResponse2 = userService.signUp(signUpRequest2);

        System.out.println("❤️❤️❤️");
        User findUser1 = userSearchService.findById(userResponse1.getId());
        User findUser2 = userSearchService.findById(userResponse2.getId());
        System.out.println("❤️❤️");
        findUser1.updatePoint(1000);
        findUser2.updatePoint(1000);
        System.out.println("❤️");
    }

}
