package com.fidelite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fidelite.dto.requests.FideliteRequestDTO;
import com.fidelite.dto.responseDTO.FideliteResponseDTO;
import com.fidelite.service.FideliteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FideliteController.class)
class FideliteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private FideliteService fideliteService;

    @Test
    void createFidelite_shouldReturn200WithResponse() throws Exception {
        FideliteRequestDTO request = FideliteRequestDTO.builder()
                .nom("Yılmaz").prenom("Ahmet")
                .mailClient("ahmet@gmail.com").password("123")
                .build();

        FideliteResponseDTO response = FideliteResponseDTO.builder()
                .idFidelite("uuid-1").barcodeFidelite("1234567890128").pointsActuels(0).build();

        when(fideliteService.createFidelite(any())).thenReturn(response);

        mockMvc.perform(post("/fidelite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFidelite").value("uuid-1"))
                .andExpect(jsonPath("$.pointsActuels").value(0));
    }

    @Test
    void getByBarcode_shouldReturn200WithFidelite() throws Exception {
        FideliteResponseDTO response = FideliteResponseDTO.builder()
                .barcodeFidelite("1234567890128").build();

        when(fideliteService.getByBarcode("1234567890128")).thenReturn(response);

        mockMvc.perform(get("/fidelite/1234567890128"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.barcodeFidelite").value("1234567890128"));
    }

    @Test
    void updatePoints_shouldReturn200WithUpdatedFidelite() throws Exception {
        FideliteResponseDTO response = FideliteResponseDTO.builder()
                .pointsActuels(150).build();

        when(fideliteService.updatePoints("uuid-1", 50)).thenReturn(response);

        mockMvc.perform(patch("/fidelite/uuid-1/points")
                        .param("points", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pointsActuels").value(150));
    }
}
