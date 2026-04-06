package com.enerz.enerz.controller;

import com.enerz.enerz.dto.DeviceRequestDTO;
import com.enerz.enerz.dto.DeviceResponseDTO;
import com.enerz.enerz.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
@Tag(name = "Dispositivos", description = "Gerenciamento de medidores IoT da empresa")
@SecurityRequirement(name = "bearerAuth")
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping
    @Operation(summary = "Cadastrar um novo dispositivo", description = "Cria um dispositivo vinculado automaticamente à empresa do usuário logado")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Dispositivo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido")
    })
    public ResponseEntity<DeviceResponseDTO> createDevice(@RequestBody @Valid DeviceRequestDTO requestDTO) {
        DeviceResponseDTO response = deviceService.createDevice(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar dispositivos", description = "Retorna todos os dispositivos cadastrados na empresa do usuário logado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Token ausente ou inválido")
    })
    public ResponseEntity<List<DeviceResponseDTO>> listDevices() {
        List<DeviceResponseDTO> devices = deviceService.listDevices();
        return ResponseEntity.ok(devices);
    }
}
