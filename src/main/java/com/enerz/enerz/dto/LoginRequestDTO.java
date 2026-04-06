package com.enerz.enerz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credenciais para autenticação")
public record LoginRequestDTO(

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        @Schema(description = "E-mail do usuário", example = "victor@enerz.com")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Schema(description = "Senha do usuário", example = "s3nh@Forte")
        String password
) {
}
