package com.gpt_hub.common.exception;

import com.gpt_hub.common.exception.custom.NotEnoughPointException;
import com.gpt_hub.common.exception.custom.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResult handleNotFoundException(NotFoundException e) {
        log.error("💣 [Exception Handler] [NotFoundException] {}", e.getMessage(), e);
        return new ErrorResult("404 NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult handleNotEnoughPointException(NotEnoughPointException e) {
        log.error("💣 [Exception Handler] [NotEnoughPointException] {}", e.getMessage(), e);
        return new ErrorResult("400 BAD_REQUEST", e.getMessage());
    }

}
