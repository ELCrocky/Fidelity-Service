package com.fidelite.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fidelite.dto.requests.ProductRequestDTO;
import com.fidelite.dto.responseDTO.ProductResponseDTO;
import com.fidelite.enums.Ptype;
import com.fidelite.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @Test
    void createProduct_shouldReturn200WithResponse() throws Exception {
        ProductRequestDTO request = ProductRequestDTO.builder()
                .name("Kahve").productType(Ptype.BOISSON).productPoint(10).build();

        ProductResponseDTO response = ProductResponseDTO.builder().name("Kahve").build();

        when(productService.createProduct(any())).thenReturn(response);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Kahve"));
    }

    @Test
    void getAllProducts_shouldReturn200WithList() throws Exception {
        ProductResponseDTO r1 = ProductResponseDTO.builder().name("Kahve").build();
        ProductResponseDTO r2 = ProductResponseDTO.builder().name("Çay").build();

        when(productService.getAllProducts()).thenReturn(List.of(r1, r2));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getPromotions_shouldReturn200WithPromotedList() throws Exception {
        ProductResponseDTO r1 = ProductResponseDTO.builder().name("Tart").build();

        when(productService.getPromotions()).thenReturn(List.of(r1));

        mockMvc.perform(get("/products/promotions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tart"));
    }

    @Test
    void setPromotion_shouldReturn200() throws Exception {
        ProductResponseDTO response = ProductResponseDTO.builder().name("Kahve").build();

        when(productService.setPromotion("Kahve", true)).thenReturn(response);

        mockMvc.perform(patch("/products/Kahve/promotion")
                        .param("promotion", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Kahve"));
    }

    @Test
    void deleteProduct_shouldReturn204() throws Exception {
        doNothing().when(productService).deleteProduct("Kahve");

        mockMvc.perform(delete("/products/Kahve"))
                .andExpect(status().isNoContent());
    }
}
