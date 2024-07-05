package com.gpt_hub.domain.pointpocket.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PointPocketResponse {

    private Long id;
    private BigDecimal points;
    private Long userId;
    private String paymentId;
    private String createdDate;

}
