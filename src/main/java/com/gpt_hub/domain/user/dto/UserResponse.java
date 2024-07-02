package com.gpt_hub.domain.user.dto;

import com.gpt_hub.domain.user.enumtype.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String loginId;
    private Role role;
    private String nickname;
    private String email;
    private double point;
    private boolean isDeleted;
    private boolean isVerified;
    private boolean isBanned;

}
