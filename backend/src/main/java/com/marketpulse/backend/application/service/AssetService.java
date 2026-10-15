package com.marketpulse.backend.application.service;

import com.marketpulse.backend.application.port.AssetRepository;
import com.marketpulse.backend.domain.model.Asset;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AssetService {

    private final AssetRepository assetRepository;


    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }
    public Asset create(String symbol, String name) {
        Asset asset = new Asset(
                null,
                symbol,
                name,
                true);

        return assetRepository.save(asset);
    }

    public List<Asset> findAll() {
        return assetRepository.findAll();
    }

}
