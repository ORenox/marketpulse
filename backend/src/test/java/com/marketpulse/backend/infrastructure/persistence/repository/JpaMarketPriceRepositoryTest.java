package com.marketpulse.backend.infrastructure.persistence.repository;


import com.marketpulse.backend.infrastructure.persistence.entity.AssetEntity;
import com.marketpulse.backend.infrastructure.persistence.entity.MarketPriceEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest(properties = {
        "spring.flyway.enabled=false"
})
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class JpaMarketPriceRepositoryTest {

    @Autowired
    private JpaMarketPriceRepository marketPriceRepository;

    @Autowired
    private JpaAssetRepository assetRepository;

    @Test
    void shouldFindPricesByAssetOrderedByTimestampDescending() {

        AssetEntity asset = assetRepository.save(
                new AssetEntity(
                        null,
                        "BTCUSDT",
                        "Bitcoin / USDT",
                        true
                )
        );

        marketPriceRepository.save(
                new MarketPriceEntity(
                        null,
                        asset,
                        new BigDecimal("65000.00"),
                        Instant.parse("2026-10-16T10:00:00Z")
                )
        );

        marketPriceRepository.save(
                new MarketPriceEntity(
                        null,
                        asset,
                        new BigDecimal("65100.00"),
                        Instant.parse("2026-10-16T11:00:00Z")
                )
        );

        List<MarketPriceEntity> prices =
                marketPriceRepository
                        .findByAsset_IdOrderByTimestampDesc(asset.getId());

        assertThat(prices).hasSize(2);

        assertThat(prices.get(0).getPrice())
                .isEqualByComparingTo("65100.00");

        assertThat(prices.get(1).getPrice())
                .isEqualByComparingTo("65000.00");
    }

    @Test
    void shouldFindLatestPrice() {

        AssetEntity asset = assetRepository.save(
                new AssetEntity(
                        null,
                        "ETHUSDT",
                        "Ethereum / USDT",
                        true
                )
        );

        marketPriceRepository.save(
                new MarketPriceEntity(
                        null,
                        asset,
                        new BigDecimal("3000.00"),
                        Instant.parse("2026-10-16T10:00:00Z")
                )
        );

        marketPriceRepository.save(
                new MarketPriceEntity(
                        null,
                        asset,
                        new BigDecimal("3100.00"),
                        Instant.parse("2026-10-16T11:00:00Z")
                )
        );

        Optional<MarketPriceEntity> latest =
                marketPriceRepository
                        .findFirstByAsset_IdOrderByTimestampDesc(
                                asset.getId()
                        );

        assertThat(latest).isPresent();

        assertThat(latest.get().getPrice())
                .isEqualByComparingTo("3100.00");
    }
}
