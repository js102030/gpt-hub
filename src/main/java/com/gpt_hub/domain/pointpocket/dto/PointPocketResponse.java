package com.gpt_hub.domain.pointpocket.dto;

import lombok.Data;

@Data
public class PointPocketResponse {

    private Long id;
    private int points;
    private Long userId;
    private String paymentId;

}
