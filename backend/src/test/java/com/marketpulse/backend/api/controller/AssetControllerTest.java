package com.marketpulse.backend.api.controller;


import com.marketpulse.backend.application.service.AssetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AssetController.class)
@Import(AssetService.class)
class AssetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateAsset() throws Exception {

        mockMvc.perform(
                        post("/api/v1/assets")
                                .contentType("application/json")
                                .content("""
                                {
                                    "symbol": "btcusdt",
                                    "name": "Bitcoin / USDT"
                                }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.symbol").value("BTCUSDT"))
                .andExpect(jsonPath("$.name").value("Bitcoin / USDT"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void shouldReturnAssets() throws Exception {

        mockMvc.perform(
                post("/api/v1/assets")
                        .contentType("application/json")
                        .content("""
                                {
                                    "symbol": "btcusdt",
                                    "name": "Bitcoin / USDT"
                                }
                                """)
        );

        mockMvc.perform(get("/api/v1/assets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].symbol").value("BTCUSDT"));
    }
}
