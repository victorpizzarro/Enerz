package com.enerz.enerz.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados de retorno de um dispositivo")
public record DeviceResponseDTO(
        @Schema(description = "ID do dispositivo", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID id,

        @Schema(description = "Nome do dispositivo", example = "Medidor Ar Condicionado Setor A")
        String name,

        @Schema(description = "Data de criação do dispositivo")
        LocalDateTime createdAt
) {
}
