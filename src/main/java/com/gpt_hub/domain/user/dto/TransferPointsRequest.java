package com.gpt_hub.domain.user.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransferPointsRequest {

    @NotNull
    private Long toUserId;

    @NotNull
    @Min(1)
    private int amount;
}
