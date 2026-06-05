package com.fidelite.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fidelite.dto.requests.TierRequestDTO;
import com.fidelite.dto.responseDTO.TierResponseDTO;
import com.fidelite.service.TierService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tiers")
@RequiredArgsConstructor
public class TierController {

    private final TierService tierService;

}
