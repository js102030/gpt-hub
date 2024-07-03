package com.gpt_hub.domain.payment.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.gpt_hub.common.annotation.LoginUserId;
import com.gpt_hub.domain.payment.dto.KakaoPayApproveResponse;
import com.gpt_hub.domain.payment.dto.KakaoPayReadyResponse;
import com.gpt_hub.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;

    @ResponseStatus(CREATED)
    @PostMapping("/payment")
    public KakaoPayReadyResponse readyKakaoPay(@LoginUserId Long loginUserId, @RequestParam int amount) {
        return paymentService.readyKakaoPay(loginUserId, amount);
    }

    @ResponseStatus(OK)
    @PatchMapping("/payment/kakaopay/refund/{paymentId}")
    public void refundKakaoPay(@LoginUserId Long loginUserId, @PathVariable String paymentId) {
        paymentService.refundKakaoPay(loginUserId, paymentId);
    }

    @ResponseStatus(OK)
    @GetMapping("/payment/kakaopay/success")
    public KakaoPayApproveResponse approveKakaoPay(@LoginUserId Long loginUserId,
                                                   @RequestParam("pg_token") String pgToken) {
        return paymentService.approveKakaoPay(loginUserId, pgToken);
    }

    @GetMapping("/payment/cancel")
    public String cancelKakaoPay() {
        return "/payment-cancel";
    }

    @GetMapping("/payment/fail")
    public String failKakaoPay() {
        return "/payment-fail";
    }

}
