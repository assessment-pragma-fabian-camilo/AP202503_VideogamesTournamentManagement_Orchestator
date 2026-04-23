package com.fc2o.api.dto.response.generic;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Error response DTO.
 */
@Builder
public record ErrorResponse(
    int code,
    String message,
    String path,
    LocalDateTime timestamp
) {
    public ErrorResponse(String message) {
        this(400, message, "", LocalDateTime.now());
    }

    public ErrorResponse(int code, String message) {
        this(code, message, "", LocalDateTime.now());
    }
}

