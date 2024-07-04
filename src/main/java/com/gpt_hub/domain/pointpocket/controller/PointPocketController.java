package com.gpt_hub.domain.pointpocket.controller;

import static org.springframework.http.HttpStatus.OK;

import com.gpt_hub.common.annotation.LoginUserId;
import com.gpt_hub.domain.pointpocket.dto.PointPocketResponse;
import com.gpt_hub.domain.pointpocket.service.PointPocketSearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PointPocketController {

    private final PointPocketSearchService pointPocketSearchService;

    @ResponseStatus(OK)
    @GetMapping("/point-pockets")
    public List<PointPocketResponse> getAllPointPockets(@LoginUserId Long loginUserId) {
        return pointPocketSearchService.findAllPointPocketsOrNull(loginUserId);
    }

    @ResponseStatus(OK)
    @GetMapping("/point")
    public int getTotPoints(@LoginUserId Long loginUserId) {
        //TODO 처음부터 sum으로 조회 가능하게 수정. 현재 null에 stream을 적용하면 NPE 발생.
        return pointPocketSearchService.findAllPointPocketsOrNull(loginUserId).stream()
                .mapToInt(PointPocketResponse::getPoints)
                .sum();
    }
}
