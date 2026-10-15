package com.marketpulse.backend.api.dto;

public record CreateAssetRequest(
        String symbol,
        String name) {
}
