package com.marketpulse.backend.infrastructure.persistence.mapper;

import com.marketpulse.backend.domain.model.Asset;
import com.marketpulse.backend.infrastructure.persistence.entity.AssetEntity;

public class AssetPersistenceMapper {

    public static AssetEntity toEntity(Asset asset) {
        return new AssetEntity(
                asset.getId(),
                asset.getSymbol(),
                asset.getName(),
                asset.isActive()
        );
    }


    public static Asset toDomain(AssetEntity entity) {

        return new Asset(
                entity.getId(),
                entity.getSymbol(),
                entity.getName(),
                entity.isActive()
        );
    }
}
