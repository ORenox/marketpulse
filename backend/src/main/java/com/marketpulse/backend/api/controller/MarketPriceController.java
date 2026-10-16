package com.marketpulse.backend.api.controller;

import com.marketpulse.backend.api.dto.CreateMarketPriceRequest;
import com.marketpulse.backend.api.dto.MarketPriceResponse;
import com.marketpulse.backend.api.mapper.MarketPriceMapper;
import com.marketpulse.backend.application.service.MarketPriceService;
import com.marketpulse.backend.domain.model.MarketPrice;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assets/{assetId}/prices")
public class MarketPriceController {

    private final MarketPriceService marketPriceService;

    public MarketPriceController(
            MarketPriceService marketPriceService
    ) {
        this.marketPriceService = marketPriceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //retorna 201 que es un created
    public MarketPriceResponse recordPrice(
            @PathVariable Long assetId,
            @Valid @RequestBody CreateMarketPriceRequest request
    ) {

        MarketPrice marketPrice =
                marketPriceService.recordPrice(
                        assetId,
                        request.price(),
                        request.timestamp()
                );

        return MarketPriceMapper.toResponse(marketPrice);
    }

    @GetMapping
    public List<MarketPriceResponse> getPrices(
            @PathVariable Long assetId
    ) {
        return marketPriceService.findByAssetId(assetId)
                .stream()
                .map(MarketPriceMapper::toResponse)
                .toList();
    }

    @GetMapping("/latest")
    public ResponseEntity<MarketPriceResponse> getLatestPrice(
            @PathVariable Long assetId
    ) {
        return marketPriceService.findLatestByAssetId(assetId)
                .map(MarketPriceMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}