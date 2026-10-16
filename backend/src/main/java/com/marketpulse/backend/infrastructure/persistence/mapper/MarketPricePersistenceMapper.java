package com.marketpulse.backend.infrastructure.persistence.mapper;

import com.marketpulse.backend.domain.model.MarketPrice;
import com.marketpulse.backend.infrastructure.persistence.entity.AssetEntity;
import com.marketpulse.backend.infrastructure.persistence.entity.MarketPriceEntity;

public class MarketPricePersistenceMapper {


    public static MarketPriceEntity toEntity(
            MarketPrice marketPrice,
            AssetEntity asset
    ) {
        return new MarketPriceEntity(
                marketPrice.getId(),
                asset,
                marketPrice.getPrice(),
                marketPrice.getTimestamp()
        );
    }

    public static MarketPrice toDomain(
            MarketPriceEntity entity
    ) {
        return new MarketPrice(
                entity.getId(),
                entity.getAsset().getId(),
                entity.getPrice(),
                entity.getTimestamp()
        );
    }


}
