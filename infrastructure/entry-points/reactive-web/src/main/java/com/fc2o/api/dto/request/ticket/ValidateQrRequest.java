package com.fc2o.api.dto.request.ticket;

import jakarta.validation.constraints.NotBlank;

public record ValidateQrRequest(
        @NotBlank(message = "QR is required")
        String qr
) {
}

