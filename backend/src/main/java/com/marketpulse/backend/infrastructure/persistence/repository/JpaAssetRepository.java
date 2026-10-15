package com.marketpulse.backend.infrastructure.persistence.repository;

import com.marketpulse.backend.infrastructure.persistence.entity.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAssetRepository
        extends JpaRepository<AssetEntity, Long> {
}
