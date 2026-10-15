package com.marketpulse.backend.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssetTest {

    @Test
    void shouldNormalizeSymbolToUpperCase() {

        Asset asset = new Asset(
                1L,
                "btcusdt",
                "Bitcoin / USDT",
                true
        );

        assertEquals("BTCUSDT", asset.getSymbol());
    }

    @Test
    void shouldRejectBlankSymbol() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Asset(
                        1L,
                        "",
                        "Bitcoin / USDT",
                        true
                )
        );
    }

    @Test
    void shouldRejectNullSymbol() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new Asset(
                        1L,
                        null,
                        "Bitcoin / USDT",
                        true
                )
        );
    }
}
