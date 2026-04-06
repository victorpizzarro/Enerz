package com.enerz.enerz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

@Schema(description = "Payload de dados de telemetria recebido do IoT")
public record IngestionRequestDTO(
        @NotNull(message = "O ID do dispositivo é obrigatório")
        @Schema(description = "ID do dispositivo que gerou a leitura", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID deviceId,

        @NotNull(message = "O consumo é obrigatório")
        @Positive(message = "O consumo deve ser um valor positivo")
        @Schema(description = "Valor lido (kWh)", example = "12.5")
        Double consumption
) {
}
