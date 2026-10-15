package com.marketpulse.backend.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAssetRequest(

        @NotBlank(message = "Symbol can not be blank")
        String symbol,

        @NotBlank(message = "Name can not be blank")
        String name) {



}
