package com.marketpulse.backend.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter; //ya me dice que use lombock para evitar usar getters manuales
import lombok.NonNull;


@Getter
public class Asset {


    private final Long id;
    private final String symbol;
    private final String name;
    private final boolean active;

    public Asset(Long id, String symbol, String name, Boolean active) {


        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Asset symbol cannot be blank");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Asset name cannot be blank");
        }


        this.id = id;
        this.symbol = symbol.toUpperCase();
        this.name = name;
        this.active = active;
    }


}
