package com.enerz.enerz.service;

import com.enerz.enerz.dto.IngestionRequestDTO;
import com.enerz.enerz.dto.IngestionResponseDTO;
import com.enerz.enerz.exception.ResourceNotFoundException;
import com.enerz.enerz.model.Device;
import com.enerz.enerz.model.EnergyData;
import com.enerz.enerz.repository.DeviceRepository;
import com.enerz.enerz.repository.EnergyDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class IngestionService {

    private final DeviceRepository deviceRepository;
    private final EnergyDataRepository energyDataRepository;

    public IngestionResponseDTO processMeasurement(IngestionRequestDTO requestDTO) {
        Device device = deviceRepository.findById(requestDTO.deviceId())
                .orElseThrow(() -> new ResourceNotFoundException("Dispositivo não encontrado com o ID: " + requestDTO.deviceId()));

        EnergyData energyData = EnergyData.builder()
                .device(device)
                .kwh(requestDTO.consumption())
                .timestamp(Instant.now())
                .build();

        EnergyData savedData = energyDataRepository.save(energyData);

        return new IngestionResponseDTO(
                savedData.getId(),
                device.getId(),
                savedData.getTimestamp(),
                savedData.getKwh()
        );
    }
}
