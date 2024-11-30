package com.apod.backend.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto<T> {
    private final Boolean success;
    private final String message;
    private final T info;

    public ResponseDto(Boolean success, String message, T info) {
        this.success = success;
        this.message = message;
        this.info = info;
    }
}
