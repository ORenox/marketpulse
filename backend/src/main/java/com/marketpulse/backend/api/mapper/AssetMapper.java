package com.marketpulse.backend.api.mapper;

import com.marketpulse.backend.api.dto.AssetResponse;
import com.marketpulse.backend.domain.model.Asset;

public class AssetMapper {

    public static AssetResponse toResponse(Asset asset) {
        return new AssetResponse(
                asset.getId(),
                asset.getSymbol(),
                asset.getName(),
                asset.isActive()
        );
    }
}
