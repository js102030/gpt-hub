package com.gpt_hub.common.security;

import com.gpt_hub.common.exception.custom.NotFoundException;
import com.gpt_hub.domain.user.entity.User;
import com.gpt_hub.domain.user.service.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserSearchService userSearchService;

    @Override
    public UserDetails loadUserByUsername(String userLoginId) throws UsernameNotFoundException {
        User findUser = userSearchService.findByLoginId(userLoginId);

        if (findUser.isDeleted()) {
            throw new NotFoundException(String.format("로그인 아이디 %s 에 해당하는 사용자는 탈퇴한 사용자입니다.", findUser.getLoginId()));
        }

        return new CustomUserDetails(findUser);
    }

}
