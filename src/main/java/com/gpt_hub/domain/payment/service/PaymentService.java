package com.gpt_hub.domain.payment.service;

import com.gpt_hub.domain.payment.dto.KakaoPayApproveResponse;
import com.gpt_hub.domain.payment.dto.KakaoPayReadyResponse;
import com.gpt_hub.domain.payment.entity.Payment;
import com.gpt_hub.domain.payment.repository.PaymentRepository;
import com.gpt_hub.domain.pointpocket.entity.PointPocket;
import com.gpt_hub.domain.pointpocket.service.PointPocketSearchService;
import com.gpt_hub.domain.pointpocket.service.PointPocketService;
import com.gpt_hub.domain.user.entity.User;
import com.gpt_hub.domain.user.service.UserSearchService;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Transactional
public class PaymentService {

    private final String CID_TEST_CODE = "TC0ONETIME";
    private final String KAKAO_PAY_APPROVE_URL = "https://open-api.kakaopay.com/online/v1/payment/approve";
    private final String KAKAO_PAY_CANCEL_URL = "https://open-api.kakaopay.com/online/v1/payment/cancel";
    private final String SECRET_KEY;

    private final UserSearchService userSearchService;
    private final PaymentSearchService paymentSearchService;
    private final PointPocketService pointPocketService;
    private final PointPocketSearchService pointPocketSearchService;
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(@Value("${kakaopay.secret-key}") String SECRET_KEY,
                          UserSearchService userSearchService, PaymentSearchService paymentSearchService,
                          PointPocketService pointPocketService, PaymentRepository paymentRepository,
                          PointPocketSearchService pointPocketSearchService) {
        this.SECRET_KEY = SECRET_KEY;
        this.userSearchService = userSearchService;
        this.paymentSearchService = paymentSearchService;
        this.pointPocketService = pointPocketService;
        this.paymentRepository = paymentRepository;
        this.pointPocketSearchService = pointPocketSearchService;
    }

    public KakaoPayReadyResponse readyKakaoPay(Long loginUserId, int amount) {
        User findUser = userSearchService.findById(loginUserId);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        final String paymentId = UUID.randomUUID().toString().replace("-", "").substring(0, 18);

        Map<String, String> params = new HashMap<>();
        params.put("cid", CID_TEST_CODE);
        params.put("partner_order_id", paymentId); // OrderID를 넣어야 하지만 Order 테이블을 만들지 않을 예정이므로 paymentId로 대체
        params.put("partner_user_id", findUser.getNickname());
        params.put("item_name", "GPT-Hub 포인트");
        params.put("quantity", "1");
        params.put("total_amount", amount + "");
        params.put("tax_free_amount", amount + "");
        params.put("approval_url", "http://localhost:8080/api/payment/kakaopay/success"); // 성공시 redirect url
        params.put("cancel_url", "http://localhost:8080/api/payment/cancel"); // 취소시 redirect url
        params.put("fail_url", "http://localhost:8080/api/payment/fail"); // 실패시 redirect url

        HttpEntity<Map<String, String>> body = new HttpEntity<>(params, getHeader());

        KakaoPayReadyResponse kakaoPayReadyResponse = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/ready", body,
                KakaoPayReadyResponse.class);

        Payment newPayment = new Payment(paymentId, findUser, amount, kakaoPayReadyResponse.getTid());
        paymentRepository.save(newPayment);
        return kakaoPayReadyResponse;
    }

    public KakaoPayApproveResponse approveKakaoPay(Long loginUserId, String pgToken) {
        Payment findPayment = paymentSearchService.findRecentUnpaidPaymentByUserId(loginUserId);
        User findUser = userSearchService.findById(loginUserId);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        Map<String, String> params = new HashMap<>();
        params.put("cid", CID_TEST_CODE);
        params.put("tid", findPayment.getDetails()); // OrderID를 넣어야 하지만 Order 테이블을 만들지 않을 예정이므로 paymentId로 대체
        params.put("partner_order_id", findPayment.getId());
        params.put("partner_user_id", findUser.getNickname());
        params.put("pg_token", pgToken);

        HttpEntity<Map<String, String>> body = new HttpEntity<>(params, getHeader());

        findPayment.completePayment();

        PointPocket newPointPocket =
                pointPocketService.createPaymentPointPocket(findUser.getId(), findPayment.getId());

        newPointPocket.addPoints(BigDecimal.valueOf(findPayment.getAmount()));

        return restTemplate.postForObject(KAKAO_PAY_APPROVE_URL, body, KakaoPayApproveResponse.class);
    }

    public void refundKakaoPay(Long loginUserId, String paymentId) {
        Payment findPayment = paymentSearchService.findByIdAndUserId(paymentId, loginUserId);
        PointPocket findPointPocket = pointPocketSearchService.findPointPocketByPaymentId(paymentId);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        Map<String, String> params = new HashMap<>();
        params.put("cid", CID_TEST_CODE);
        params.put("tid", findPayment.getDetails());
        params.put("cancel_amount", String.valueOf(findPointPocket.getPoints()));
        params.put("cancel_tax_free_amount", String.valueOf(findPointPocket.getPoints()));

        HttpEntity<Map<String, String>> body = new HttpEntity<>(params, getHeader());

        restTemplate.postForObject(KAKAO_PAY_CANCEL_URL, body, String.class);

        findPointPocket.refundPoints();
        findPayment.refundPayment();
    }

    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "SECRET_KEY " + SECRET_KEY);
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        return headers;
    }

}
