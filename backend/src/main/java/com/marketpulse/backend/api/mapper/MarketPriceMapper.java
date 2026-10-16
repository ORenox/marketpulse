package com.marketpulse.backend.api.mapper;

import com.marketpulse.backend.api.dto.MarketPriceResponse;
import com.marketpulse.backend.domain.model.MarketPrice;

public class MarketPriceMapper {

    public static MarketPriceResponse toResponse(
            MarketPrice marketPrice
    ) {
        return new MarketPriceResponse(
                marketPrice.getId(),
                marketPrice.getAssetId(),
                marketPrice.getPrice(),
                marketPrice.getTimestamp()
        );
    }
}