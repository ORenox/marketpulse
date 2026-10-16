package com.marketpulse.backend.application.port;

import com.marketpulse.backend.domain.model.MarketPrice;

import java.util.List;
import java.util.Optional;

public interface MarketPriceRepository {

    MarketPrice save(MarketPrice marketPrice);
    List<MarketPrice> findByAssetId(Long assetId);
    Optional<MarketPrice> findLatestByAssetId(Long assetId);
}
