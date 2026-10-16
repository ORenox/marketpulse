package com.marketpulse.backend.application.service;


import com.marketpulse.backend.application.port.MarketPriceRepository;
import com.marketpulse.backend.domain.model.MarketPrice;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class MarketPriceService {

    private final MarketPriceRepository marketPriceRepository;

    public MarketPriceService(
            MarketPriceRepository marketPriceRepository) {
        this.marketPriceRepository = marketPriceRepository;
    }

    public MarketPrice recordPrice(
        Long assetId,
        BigDecimal price,
        Instant timestamp
    ){
        MarketPrice marketPrice = new MarketPrice(
                null,
                assetId,
                price,
                timestamp
        );

        return marketPriceRepository.save(marketPrice);
    }

    public List<MarketPrice> findByAssetId(Long assetId) {

        return marketPriceRepository.findByAssetId(assetId);
    }

    public Optional<MarketPrice> findLatestByAssetId(Long assetId) {

        return marketPriceRepository.findLatestByAssetId(assetId);
    }

}
