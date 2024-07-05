package com.gpt_hub.domain.pointtransfer.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.gpt_hub.common.annotation.LoginUserId;
import com.gpt_hub.domain.pointtransfer.dto.PointTransferRequest;
import com.gpt_hub.domain.pointtransfer.dto.PointTransferResponse;
import com.gpt_hub.domain.pointtransfer.service.PointTransferSearchService;
import com.gpt_hub.domain.pointtransfer.service.PointTransferService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PointTransferController {

    private final PointTransferService pointTransferService;
    private final PointTransferSearchService pointTransferSearchService;

    @ResponseStatus(CREATED)
    @PostMapping("/point-transfers")
    public PointTransferResponse transferPoints(@LoginUserId Long loginUserId,
                                                @RequestBody PointTransferRequest request) {
        return pointTransferService.transferPoints(loginUserId, request);
    }

    @ResponseStatus(OK)
    @GetMapping("/point-transfers")
    public List<PointTransferResponse> getAllPointTransfers(@LoginUserId Long loginUserId) {
        return pointTransferSearchService.getAllPointTransfers(loginUserId);
    }

}
