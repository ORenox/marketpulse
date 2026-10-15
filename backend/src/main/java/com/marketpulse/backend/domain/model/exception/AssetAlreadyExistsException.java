package com.marketpulse.backend.domain.model.exception;

public class AssetAlreadyExistsException extends RuntimeException {
    public AssetAlreadyExistsException(String symbol) {
        super("Asset with symbol " + symbol + " already exists");
    }
}
