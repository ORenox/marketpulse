package com.marketpulse.backend.api.dto;

public record HealthResponse(
        String status,
        String service
) {
}
