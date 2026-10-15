package com.marketpulse.backend.api.dto;

import java.util.Map;

public record ErrorResponse (
        int status,
        String message,
        Map<String,String> errors
){
}
