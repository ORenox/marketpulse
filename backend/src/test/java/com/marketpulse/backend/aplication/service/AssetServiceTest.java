package com.marketpulse.backend.aplication.service;

import com.marketpulse.backend.application.service.AssetService;
import com.marketpulse.backend.domain.model.Asset;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class AssetServiceTest {

    private final AssetService assetService = new AssetService();

    @Test
    void shouldCreateAsset() {

        Asset asset = assetService.create(
                "btcusdt",
                "Bitcoin / USDT"
        );

        assertNotNull(asset);
        assertEquals(1L, asset.getId());
        assertEquals("BTCUSDT", asset.getSymbol());
        assertEquals("Bitcoin / USDT", asset.getName());
        assertTrue(asset.isActive());
    }

    @Test
    void shouldReturnCreatedAssets() {

        assetService.create(
                "btcusdt",
                "Bitcoin / USDT"
        );

        assetService.create(
                "ethusdt",
                "Ethereum / USDT"
        );

        Collection<Asset> assets = assetService.findAll();

        assertEquals(2, assets.size());
    }
}