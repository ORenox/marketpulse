CREATE TABLE market_prices (
                               id BIGSERIAL PRIMARY KEY,
                               asset_id BIGINT NOT NULL,
                               price NUMERIC(20,8) NOT NULL,
                               timestamp TIMESTAMP WITH TIME ZONE NOT NULL,

                               CONSTRAINT fk_market_prices_asset
                                   FOREIGN KEY (asset_id)
                                       REFERENCES assets(id)
);

CREATE INDEX idx_market_prices_asset_timestamp
    ON market_prices(asset_id, timestamp);