package com.gpt_hub.common.security;

import static com.gpt_hub.domain.pointearn.enumtype.ActivityType.LOGIN;

import com.gpt_hub.domain.pointearn.event.UserActionEvent;
import com.gpt_hub.domain.user.entity.User;
import com.gpt_hub.domain.user.service.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserSearchService userSearchService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public UserDetails loadUserByUsername(String userLoginId) throws UsernameNotFoundException {
        User findUser = userSearchService.findByLoginId(userLoginId);

        eventPublisher.publishEvent(new UserActionEvent(this, findUser.getId(), LOGIN));

        return new CustomUserDetails(findUser);
    }

}
