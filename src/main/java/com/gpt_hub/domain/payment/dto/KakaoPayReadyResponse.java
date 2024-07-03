package com.gpt_hub.domain.payment.dto;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KakaoPayReadyResponse {

    private String tid;
    private String next_redirect_pc_url;
    private Date created_at;

}
