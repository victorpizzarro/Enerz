package com.enerz.enerz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados para criação de um novo dispositivo")
public record DeviceRequestDTO(
        @NotBlank(message = "O nome do dispositivo é obrigatório")
        @Schema(description = "Nome do dispositivo", example = "Medidor Ar Condicionado Setor A")
        String name
) {
}
