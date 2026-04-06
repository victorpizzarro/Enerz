package com.enerz.enerz.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Resposta de sucesso após ingestão do dado")
public record IngestionResponseDTO(
        @Schema(description = "ID da leitura processada")
        UUID measurementId,

        @Schema(description = "ID do dispositivo")
        UUID deviceId,

        @Schema(description = "Data e hora exata da leitura", example = "2024-03-01T12:00:00Z")
        Instant timestamp,

        @Schema(description = "Valor lido", example = "12.5")
        Double consumption
) {
}
