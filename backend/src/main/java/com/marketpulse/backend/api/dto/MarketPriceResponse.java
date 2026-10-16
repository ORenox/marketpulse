package com.marketpulse.backend.api.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record MarketPriceResponse(
        Long id,
        Long assetId,
        BigDecimal price,
        Instant timestamp
) {
}