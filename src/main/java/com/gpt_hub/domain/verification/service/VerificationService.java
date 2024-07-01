package com.gpt_hub.domain.verification.service;

import com.gpt_hub.common.exception.custom.ExistException;
import com.gpt_hub.common.exception.custom.NotFoundException;
import com.gpt_hub.domain.user.entity.User;
import com.gpt_hub.domain.user.service.UserSearchService;
import com.gpt_hub.domain.user.service.UserService;
import com.gpt_hub.domain.verification.dto.VerifyRequest;
import com.gpt_hub.domain.verification.entity.Verification;
import com.gpt_hub.domain.verification.repository.VerificationRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VerificationService {

    private final UserSearchService userSearchService;
    private final UserService userService;
    private final VerificationRepository verificationRepository;
    private final JavaMailSender emailSender;

    @Value("${naver.email}")
    private String naverEmail;

    public String sendVerificationCode(Long userId, String email) throws Exception {
        if (userSearchService.existsByEmail(email)) {
            throw new ExistException("이미 가입된 이메일입니다.");
        }

        String code = createKey();
        MimeMessage message = createMessage(email, code);
        emailSender.send(message);

        Verification verification = createVerification(code, userId);

        verificationRepository.save(verification);

        return code;
    }

    public void activateEmailVerification(Long userId, VerifyRequest verifyRequest) {
        Verification verification = findVerificationByCodeAndUserId(verifyRequest.getCode(), userId);

        ensureVerificationNotExpired(verification);

        activateUserEmailVerification(userId, verifyRequest.getEmail());
    }

    private Verification findVerificationByCodeAndUserId(String code, Long userId) {
        return verificationRepository.findByVerificationCodeAndUserId(code, userId)
                .orElseThrow(() -> new NotFoundException("인증코드가 일치하지 않습니다."));
    }

    private void ensureVerificationNotExpired(Verification verification) {
        if (verification.getExpiryDate().before(new Date())) {
            throw new NotFoundException("인증코드가 만료되었습니다.");
        }
    }

    private void activateUserEmailVerification(Long userId, String email) {
        userService.updateVerify(userId, email);
    }

    private String createKey() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "").substring(0, 12);
    }

    private Date generateExpiryDate() {
        final int EXPIRY_DURATION_MINUTES = 1; // 1분 뒤 만료

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, EXPIRY_DURATION_MINUTES);

        return calendar.getTime();
    }

    private Verification createVerification(String code, Long userId) {
        User findUser = userSearchService.findById(userId);
        Date date = generateExpiryDate();

        return new Verification(findUser, code, date);
    }

    private MimeMessage createMessage(String to, String code) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);
        message.setSubject("GPT-Hub 이메일 인증");

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요</h1>";
        msgg += "<h1> GPT-Hub 입니다.</h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 이메일 인증 박스에 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<p>GPT-Hub를 이용해주셔서 감사합니다!<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += code + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");

        message.setFrom(new InternetAddress(naverEmail + "@naver.com", "GPT-Hub"));

        return message;
    }
}
