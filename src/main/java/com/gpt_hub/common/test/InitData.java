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

        SignUpRequest signUpRequest1 = new SignUpRequest();
        SignUpRequest signUpRequest2 = new SignUpRequest();

        signUpRequest1.setLoginId("test1");
        signUpRequest1.setPassword("test1");
        signUpRequest1.setNickname("test1");

        signUpRequest2.setLoginId("test2");
        signUpRequest2.setPassword("test2");
        signUpRequest2.setNickname("test2");

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
