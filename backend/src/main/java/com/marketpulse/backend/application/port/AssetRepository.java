package com.marketpulse.backend.application.port;

import com.marketpulse.backend.domain.model.Asset;

import java.util.List;

public interface AssetRepository {

    Asset save(Asset asset);
    List<Asset> findAll();

}
