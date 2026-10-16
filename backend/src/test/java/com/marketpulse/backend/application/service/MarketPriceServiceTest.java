package com.marketpulse.backend.application.service;

import com.marketpulse.backend.application.port.MarketPriceRepository;
import com.marketpulse.backend.domain.model.MarketPrice;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class MarketPriceServiceTest {
    @Mock
    private MarketPriceRepository marketPriceRepository;

    @InjectMocks
    private MarketPriceService marketPriceService;

    @Test
    void shouldRecordMarketPrice() {

        Instant timestamp = Instant.parse(
                "2026-10-15T20:00:00Z"
        );

        MarketPrice marketPrice = new MarketPrice(
                1L,
                1L,
                new BigDecimal("104523.50"),
                timestamp
        );

        when(marketPriceRepository.save(any(MarketPrice.class)))
                .thenReturn(marketPrice);

        MarketPrice result = marketPriceService.recordPrice(
                1L,
                new BigDecimal("104523.50"),
                timestamp
        );

        assertEquals(
                new BigDecimal("104523.50"),
                result.getPrice()
        );

        assertEquals(
                1L,
                result.getAssetId()
        );

        assertEquals(
                timestamp,
                result.getTimestamp()
        );

        verify(marketPriceRepository).save(any(MarketPrice.class));
    }
}
