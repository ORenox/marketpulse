package com.marketpulse.backend.application.service;

import com.marketpulse.backend.application.port.AssetRepository;
import com.marketpulse.backend.domain.model.Asset;
import com.marketpulse.backend.domain.model.exception.AssetAlreadyExistsException;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AssetService {

    private final AssetRepository assetRepository;


    public AssetService(AssetRepository assetRepository) {

        this.assetRepository = assetRepository;
    }
    public Asset create(String symbol, String name) {

        if(assetRepository.existsBySymbol(symbol)) {
            throw new AssetAlreadyExistsException(symbol);
        }
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
