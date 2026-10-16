package com.marketpulse.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
public class MarketPrice {


    private final Long id;
    private final Long assetId;
    private final BigDecimal price;
    private final Instant timestamp;


    public MarketPrice(
            Long id,
            Long assetId,
            BigDecimal price,
            Instant timestamp
    ) {

        if(assetId == null) {
            throw new IllegalArgumentException("Asset ID cannot be null");
        }
        if(price == null || price.signum() < 0) {
            throw new IllegalArgumentException("Price must be greater than or equal to 0");
        }
        if(timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }

        this.id = id;
        this.assetId = assetId;
        this.price = price;
        this.timestamp = timestamp;
    }
}
