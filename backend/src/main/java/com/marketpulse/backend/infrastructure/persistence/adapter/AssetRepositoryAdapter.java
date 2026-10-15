package com.marketpulse.backend.infrastructure.persistence.adapter;

import com.marketpulse.backend.application.port.AssetRepository;
import com.marketpulse.backend.domain.model.Asset;
import com.marketpulse.backend.infrastructure.persistence.entity.AssetEntity;
import com.marketpulse.backend.infrastructure.persistence.mapper.AssetPersistenceMapper;
import com.marketpulse.backend.infrastructure.persistence.repository.JpaAssetRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class AssetRepositoryAdapter implements AssetRepository {

    private final JpaAssetRepository jpaAssetRepository;

    public AssetRepositoryAdapter(JpaAssetRepository jpaAssetRepository) {
        this.jpaAssetRepository = jpaAssetRepository;
    }


    @Override
    public Asset save(Asset asset) {
        AssetEntity entity = AssetPersistenceMapper.toEntity(asset);

        AssetEntity savedEntity = jpaAssetRepository.save(entity);

        return  AssetPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public List<Asset> findAll() {
        return jpaAssetRepository.findAll()
                .stream()
                .map(AssetPersistenceMapper::toDomain)
                .toList();
    }
}
