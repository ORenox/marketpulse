package com.marketpulse.backend.application.service;

import com.marketpulse.backend.application.port.AssetRepository;
import com.marketpulse.backend.application.service.AssetService;
import com.marketpulse.backend.domain.model.Asset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AssetServiceTest {

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private AssetService assetService;

    @Test
    void shouldCreateAsset() {

        Asset savedAsset = new Asset(
                1L,
                "BTCUSDT",
                "Bitcoin / USDT",
                true
        );

        when(assetRepository.save(any(Asset.class)))
                .thenReturn(savedAsset);

        Asset asset = assetService.create(
                "btcusdt",
                "Bitcoin / USDT"
        );

        assertNotNull(asset);
        assertEquals(1L, asset.getId());
        assertEquals("BTCUSDT", asset.getSymbol());
        assertEquals("Bitcoin / USDT", asset.getName());
        assertTrue(asset.isActive());

        verify(assetRepository).save(any(Asset.class));
    }

    @Test
    void shouldReturnCreatedAssets() {

        List<Asset> assets = List.of(
                new Asset(
                        1L,
                        "BTCUSDT",
                        "Bitcoin / USDT",
                        true
                ),
                new Asset(
                        2L,
                        "ETHUSDT",
                        "Ethereum / USDT",
                        true
                )
        );

        when(assetRepository.findAll())
                .thenReturn(assets);

        List<Asset> result = assetService.findAll();

        assertEquals(2, result.size());
        assertEquals("BTCUSDT", result.get(0).getSymbol());
        assertEquals("ETHUSDT", result.get(1).getSymbol());

        verify(assetRepository).findAll();
    }
}