package com.enerz.enerz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro de novo usuário e empresa")
public record RegisterRequestDTO(

        @NotBlank(message = "O nome é obrigatório")
        @Schema(description = "Nome completo do usuário", example = "Victor Pizarro")
        String name,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        @Schema(description = "E-mail do usuário", example = "victor@enerz.com")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        @Schema(description = "Senha do usuário (mínimo 6 caracteres)", example = "s3nh@Forte")
        String password,

        @NotBlank(message = "O nome da empresa é obrigatório")
        @Schema(description = "Nome da empresa", example = "Enerz Ltda")
        String companyName
) {
}
