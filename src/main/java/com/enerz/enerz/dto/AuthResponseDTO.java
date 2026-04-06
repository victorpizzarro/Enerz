package com.enerz.enerz.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta de autenticação contendo token JWT e dados do usuário")
public record AuthResponseDTO(

        @Schema(description = "Token JWT para autenticação", example = "eyJhbGciOiJIUzI1NiJ9...")
        String token,

        @Schema(description = "Dados do usuário autenticado")
        UserResponseDTO user
) {

    public static AuthResponseDTO from(String token, UserResponseDTO user) {
        return new AuthResponseDTO(token, user);
    }
}
