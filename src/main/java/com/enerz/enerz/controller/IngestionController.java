package com.enerz.enerz.controller;

import com.enerz.enerz.dto.IngestionRequestDTO;
import com.enerz.enerz.dto.IngestionResponseDTO;
import com.enerz.enerz.service.IngestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ingestion")
@RequiredArgsConstructor
@Tag(name = "Ingestão", description = "Endpoint de máquina para máquina para recepção de telemetria")
public class IngestionController {

    private final IngestionService ingestionService;

    @PostMapping
    @Operation(summary = "Enviar leitura de consumo", description = "Recebe os dados de consumo de um dispositivo IoT específico")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Leitura salva com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação (ex: consumo negativo)"),
            @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado")
    })
    public ResponseEntity<IngestionResponseDTO> ingestData(@RequestBody @Valid IngestionRequestDTO requestDTO) {
        IngestionResponseDTO response = ingestionService.processMeasurement(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
