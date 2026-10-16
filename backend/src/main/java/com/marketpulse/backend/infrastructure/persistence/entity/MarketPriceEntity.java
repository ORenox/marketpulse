package com.marketpulse.backend.infrastructure.persistence.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(
        name="market_prices",
        indexes = {
                @Index(
                        name = "idx_market_prices_asset_timestamp",
                        columnList = "asset_id, timestamp")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MarketPriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "asset_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_market_prices_assets")
    )
    private AssetEntity asset;



    @Column(nullable = false, precision = 20, scale = 8)
    private BigDecimal price;

    @Column(nullable = false)
    private Instant timestamp;
}
