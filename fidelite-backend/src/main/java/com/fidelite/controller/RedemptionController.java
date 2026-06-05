package com.fidelite.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fidelite.dto.requests.RedemptionRequestDTO;
import com.fidelite.dto.responseDTO.RedemptionResponseDTO;
import com.fidelite.service.RedemptionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/redemptions")
@RequiredArgsConstructor
public class RedemptionController {

    private final RedemptionService redemptionService;

}
