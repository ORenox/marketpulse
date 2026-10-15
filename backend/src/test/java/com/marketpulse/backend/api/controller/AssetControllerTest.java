package com.marketpulse.backend.api.controller;


import com.marketpulse.backend.application.service.AssetService;
import com.marketpulse.backend.domain.model.Asset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AssetController.class)
class AssetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AssetService assetService;

    @Test
    void shouldCreateAsset() throws Exception {

        Asset asset = new Asset(
                1L,
                "BTCUSDT",
                "Bitcoin / USDT",
                true
        );

        when(assetService.create(anyString(), anyString()))
                .thenReturn(asset);

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
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.symbol").value("BTCUSDT"))
                .andExpect(jsonPath("$.name").value("Bitcoin / USDT"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    void shouldReturnAssets() throws Exception {

        when(assetService.findAll())
                .thenReturn(java.util.List.of(
                        new Asset(
                                1L,
                                "BTCUSDT",
                                "Bitcoin / USDT",
                                true
                        )
                ));

        mockMvc.perform(get("/api/v1/assets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].symbol").value("BTCUSDT"));
    }
}