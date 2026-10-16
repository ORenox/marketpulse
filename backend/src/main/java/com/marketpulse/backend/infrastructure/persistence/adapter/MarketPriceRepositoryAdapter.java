package com.marketpulse.backend.infrastructure.persistence.adapter;

import com.marketpulse.backend.application.port.MarketPriceRepository;
import com.marketpulse.backend.domain.model.MarketPrice;
import com.marketpulse.backend.infrastructure.persistence.entity.AssetEntity;
import com.marketpulse.backend.infrastructure.persistence.entity.MarketPriceEntity;
import com.marketpulse.backend.infrastructure.persistence.mapper.MarketPricePersistenceMapper;
import com.marketpulse.backend.infrastructure.persistence.repository.JpaAssetRepository;
import com.marketpulse.backend.infrastructure.persistence.repository.JpaMarketPriceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MarketPriceRepositoryAdapter implements MarketPriceRepository {
    private final JpaMarketPriceRepository jpaMarketPriceRepository;
    private final JpaAssetRepository jpaAssetRepository;

    public MarketPriceRepositoryAdapter(
            JpaMarketPriceRepository jpaMarketPriceRepository,
            JpaAssetRepository jpaAssetRepository
    ) {
        this.jpaMarketPriceRepository = jpaMarketPriceRepository;
        this.jpaAssetRepository = jpaAssetRepository;
    }

    @Override
    public MarketPrice save(MarketPrice marketPrice) {

        AssetEntity asset =
                jpaAssetRepository.findById(marketPrice.getAssetId())
                        .orElseThrow(() -> new RuntimeException("Asset not found with id: " + marketPrice.getAssetId()));

        MarketPriceEntity entity =
                MarketPricePersistenceMapper.toEntity(marketPrice,asset);

        MarketPriceEntity savedEntity =
                jpaMarketPriceRepository.save(entity);

        return MarketPricePersistenceMapper.toDomain(
                savedEntity
        );
    }

    @Override
    public List<MarketPrice> findByAssetId(Long assetId) {
        return jpaMarketPriceRepository
                .findByAsset_IdOrderByTimestampDesc(assetId)
                .stream()
                .map(MarketPricePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<MarketPrice> findLatestByAssetId(Long assetId) {
        return jpaMarketPriceRepository
                .findFirstByAsset_IdOrderByTimestampDesc(assetId)
                .map(MarketPricePersistenceMapper::toDomain);
    }
}
