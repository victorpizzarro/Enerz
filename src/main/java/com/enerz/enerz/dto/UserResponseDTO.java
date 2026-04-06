package com.enerz.enerz.dto;

import com.enerz.enerz.model.Role;
import com.enerz.enerz.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados do usuário retornados pela API (sem senha)")
public record UserResponseDTO(

        @Schema(description = "ID único do usuário", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
        UUID id,

        @Schema(description = "Nome do usuário", example = "Victor Pizarro")
        String name,

        @Schema(description = "E-mail do usuário", example = "victor@enerz.com")
        String email,

        @Schema(description = "Papel do usuário no sistema", example = "USER")
        Role role,

        @Schema(description = "Nome da empresa associada", example = "Enerz Ltda")
        String companyName,

        @Schema(description = "ID da empresa associada")
        UUID companyId,

        @Schema(description = "Data de criação do registro")
        LocalDateTime createdAt,

        @Schema(description = "Data da última atualização")
        LocalDateTime updatedAt
) {

    public static UserResponseDTO from(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getCompany() != null ? user.getCompany().getName() : null,
                user.getCompany() != null ? user.getCompany().getId() : null,
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
