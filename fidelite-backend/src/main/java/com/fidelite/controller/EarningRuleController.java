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

import com.fidelite.dto.requests.EarningRuleRequestDTO;
import com.fidelite.dto.responseDTO.EarningRuleResponseDTO;
import com.fidelite.service.EarningRuleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/earning-rules")
@RequiredArgsConstructor
public class EarningRuleController {

    private final EarningRuleService earningRuleService;

}
