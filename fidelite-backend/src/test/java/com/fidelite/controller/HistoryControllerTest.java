package com.fidelite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fidelite.dto.requests.HistoryRequestDTO;
import com.fidelite.dto.responseDTO.HistoryResponseDTO;
import com.fidelite.service.HistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HistoryController.class)
class HistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private HistoryService historyService;

    @Test
    void addTransaction_shouldReturn200WithResponse() throws Exception {
        HistoryRequestDTO request = HistoryRequestDTO.builder()
                .points(50).transactionType("GAIN").products(List.of("Kahve")).build();

        HistoryResponseDTO response = HistoryResponseDTO.builder()
                .points(50).transactionId(1).build();

        when(historyService.addTransaction(eq("fidelite-1"), any())).thenReturn(response);

        mockMvc.perform(post("/history/fidelite-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(50))
                .andExpect(jsonPath("$.transactionId").value(1));
    }

    @Test
    void getHistoryByFidelite_shouldReturn200WithList() throws Exception {
        HistoryResponseDTO r1 = HistoryResponseDTO.builder().points(10).build();
        HistoryResponseDTO r2 = HistoryResponseDTO.builder().points(20).build();

        when(historyService.getHistoryByFidalite("fidelite-1")).thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/history/fidelite-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].points").value(10));
    }
}
