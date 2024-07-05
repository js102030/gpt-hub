package com.gpt_hub.domain.pointtransfer.dto;

import lombok.Data;

@Data
public class PointTransferRequest {

    private Long receiverId;
    private int amount;
    private String message;

}
