package com.marketpulse.backend.api.controller;


import com.marketpulse.backend.application.service.AssetService;
import com.marketpulse.backend.domain.model.Asset;
import com.marketpulse.backend.domain.model.exception.AssetAlreadyExistsException;
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

    @Test
    void shouldRejectBlankSymbol() throws Exception {

        mockMvc.perform(
            post("/api/v1/assets")
                    .contentType("application/json")
                    .content("""
                            {
                                "symbol": "",
                                "name": "Bitcoin / USDT"
                            }
                            """)
        ).andDo( result -> {
                    System.out.println("STATUS: " + result.getResponse().getStatus());
                    System.out.println("BODY: " + result.getResponse().getContentAsString());
                })
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.symbol").value("Symbol can not be blank"));
    }

    @Test
    void shouldRejectBlankName() throws Exception {

        mockMvc.perform(
                post("/api/v1/assets")
                        .contentType("application/json")
                        .content("""
                            {
                                "symbol": "BTCUSDT",
                                "name": ""
                            }
                            """)
        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors.name")
                        .value("Name can not be blank"));
    }

    @Test
    void shouldRejectDuplicateAsset() throws Exception {

        when(assetService.create(
                "BTCUSDT",
                "Bitcoin / USDT"
        )).thenThrow(
                new AssetAlreadyExistsException("BTCUSDT")
        );

        mockMvc.perform(
                        post("/api/v1/assets")
                                .contentType("application/json")
                                .content("""
                            {
                                "symbol": "BTCUSDT",
                                "name": "Bitcoin / USDT"
                            }
                            """)
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message")
                        .value("Asset with symbol BTCUSDT already exists"));
    }




}