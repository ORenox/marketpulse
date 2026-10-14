package com.marketpulse.backend.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.marketpulse.backend.api.dto.HealthResponse;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    @GetMapping
    public HealthResponse health() {
        return new HealthResponse("Up", "marketpulse-backend");
    }

}
