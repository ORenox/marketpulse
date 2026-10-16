package com.marketpulse.backend.infrastructure.persistence.repository;

import com.marketpulse.backend.infrastructure.persistence.entity.MarketPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaMarketPriceRepository extends JpaRepository<MarketPriceEntity, Long> {

    List<MarketPriceEntity> findByAsset_IdOrderByTimestampDesc(
            Long assetId
    );

    Optional<MarketPriceEntity> findFirstByAsset_IdOrderByTimestampDesc(
            Long assetId
    );

}
