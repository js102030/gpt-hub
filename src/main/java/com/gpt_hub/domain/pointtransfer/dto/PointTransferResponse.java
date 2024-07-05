package com.gpt_hub.domain.pointtransfer.dto;

import lombok.Data;

@Data
public class PointTransferResponse {

    private Long id;
    private Long senderId;
    private Long receiverId;
    private int amount;
    private String message;
    private String createdDate;

}
