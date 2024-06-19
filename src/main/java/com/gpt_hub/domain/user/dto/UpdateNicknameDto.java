package com.gpt_hub.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateNicknameDto {

    @NotBlank(message = "닉네임 입력은 필수입니다.")
    private String nickname;

}
