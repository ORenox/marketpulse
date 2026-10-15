package com.marketpulse.backend.api.dto;

public record AssetResponse(
        Long id,
        String symbol,
        String name,
        boolean active
) {

}
