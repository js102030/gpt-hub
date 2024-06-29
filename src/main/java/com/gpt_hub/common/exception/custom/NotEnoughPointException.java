package com.gpt_hub.common.exception.custom;

public class NotEnoughPointException extends RuntimeException {

    public NotEnoughPointException(String message) {
        super(message);
    }

    public NotEnoughPointException(String message, Throwable cause) {
        super(message, cause);
    }
}
