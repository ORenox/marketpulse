package com.marketpulse.backend.application.service;

import com.marketpulse.backend.domain.model.Asset;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class AssetService {

    private final Map<Long, Asset> assets = new HashMap<>();

    private Long nextId = 1L;

    public Asset create(String symbol, String name) {
        Asset asset = new Asset(
                nextId++,
                symbol,
                name,
                true);
        assets.put(asset.getId(), asset);
        return asset;
    }

    public Collection<Asset> findAll() {

        return assets.values();
    }

}
