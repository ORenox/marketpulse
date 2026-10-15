package com.marketpulse.backend.infrastructure.persistence;


import com.marketpulse.backend.application.port.AssetRepository;
import com.marketpulse.backend.domain.model.Asset;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Repository
public class InMemoryAssetRepository  implements AssetRepository {

    private final Map<Long, Asset> assets = new HashMap<>();
    private Long nextId = 1L;


    @Override
    public Asset save(Asset asset) {
        Asset savedAsset = new Asset(
                nextId++,
                asset.getSymbol(),
                asset.getName(),
                asset.isActive()
        );

        assets.put(savedAsset.getId(), savedAsset);
        return savedAsset;
    }

    @Override
    public List<Asset> findAll() {
        return new ArrayList<>(assets.values());
    }


}
