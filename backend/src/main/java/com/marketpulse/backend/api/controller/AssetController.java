package com.marketpulse.backend.api.controller;

import com.marketpulse.backend.api.dto.AssetResponse;
import com.marketpulse.backend.api.dto.CreateAssetRequest;
import com.marketpulse.backend.api.mapper.AssetMapper;
import com.marketpulse.backend.application.service.AssetService;
import com.marketpulse.backend.domain.model.Asset;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    public AssetResponse createAsset(
            @RequestBody CreateAssetRequest request) {
        Asset asset = assetService.create(
                request.symbol(),
                request.name()
        );
        return AssetMapper.toResponse(asset);
    }

    @GetMapping
    public List<AssetResponse> getAssets() {
        return assetService.findAll().stream().map(AssetMapper::toResponse).toList();
    }

}
