package com.marketpulse.backend.api.controller;

import com.marketpulse.backend.application.service.MarketPriceService;
import com.marketpulse.backend.domain.model.MarketPrice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MarketPriceController.class)
public class MarketPriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MarketPriceService marketPriceService;

    @Test
    void shouldRecordMarketPrice() throws Exception {
        Instant timestamp = Instant.parse("2026-10-16T18:00:00Z");

        MarketPrice marketPrice = new MarketPrice(
                1L,
                1L,
                new BigDecimal("104523.54"),
                timestamp
        );

        when(marketPriceService.recordPrice(
                eq(1L),
                eq(new BigDecimal("104523.54")),
                eq(timestamp)
        )).thenReturn(marketPrice);

        mockMvc.perform(
                post("/api/v1/assets/1/prices")
                        .contentType("application/json")
                        .content("""
                                {
                                    "price": 104523.54,
                                    "timestamp": "2026-10-16T18:00:00Z"
                                }
                                """)
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.assetId").value(1))
                .andExpect(jsonPath("$.price").value(104523.54))
                .andExpect(jsonPath("$.timestamp").value("2026-10-16T18:00:00Z"));


    }

    @Test
    void shouldRejectNegativePrice() throws Exception {

        mockMvc.perform(
                        post("/api/v1/assets/1/prices")
                                .contentType("application/json")
                                .content("""
                            {
                                "price": -100.00,
                                "timestamp": "2026-10-16T18:00:00Z"
                            }
                            """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message")
                        .value("Validation failed"))
                .andExpect(jsonPath("$.errors.price")
                        .value("Price cannot be negative"));
    }


    @Test
    void shouldRejectNullTimestamp() throws Exception {

        mockMvc.perform(
                        post("/api/v1/assets/1/prices")
                                .contentType("application/json")
                                .content("""
                            {
                                "price": 104523.50,
                                "timestamp": null
                            }
                            """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message")
                        .value("Validation failed"))
                .andExpect(jsonPath("$.errors.timestamp")
                        .value("Timestamp cannot be null"));
    }

    @Test
    void shouldReturnMarketPrices() throws Exception {

        when(marketPriceService.findByAssetId(1L))
                .thenReturn(List.of(
                        new MarketPrice(
                                1L,
                                1L,
                                new BigDecimal("65000.00"),
                                Instant.parse("2026-10-16T10:00:00Z")
                        ),
                        new MarketPrice(
                                2L,
                                1L,
                                new BigDecimal("65100.00"),
                                Instant.parse("2026-10-16T11:00:00Z")
                        )
                ));

        mockMvc.perform(
                        get("/api/v1/assets/1/prices")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].price").value(65000.00))
                .andExpect(jsonPath("$[1].price").value(65100.00));
    }

    @Test
    void shouldReturnLatestMarketPrice() throws Exception {

        MarketPrice latestPrice = new MarketPrice(
                2L,
                1L,
                new BigDecimal("65100.00"),
                Instant.parse("2026-10-16T11:00:00Z")
        );

        when(marketPriceService.findLatestByAssetId(1L))
                .thenReturn(Optional.of(latestPrice));

        mockMvc.perform(
                        get("/api/v1/assets/1/prices/latest")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.assetId").value(1))
                .andExpect(jsonPath("$.price").value(65100.00));
    }

    @Test
    void shouldReturnNotFoundWhenLatestPriceDoesNotExist()
            throws Exception {

        when(marketPriceService.findLatestByAssetId(1L))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                        get("/api/v1/assets/1/prices/latest")
                )
                .andExpect(status().isNotFound());
    }

}
