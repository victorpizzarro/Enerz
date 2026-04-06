package com.enerz.enerz.service;

import com.enerz.enerz.dto.DeviceRequestDTO;
import com.enerz.enerz.dto.DeviceResponseDTO;
import com.enerz.enerz.model.Device;
import com.enerz.enerz.model.User;
import com.enerz.enerz.repository.DeviceRepository;
import com.enerz.enerz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    public DeviceResponseDTO createDevice(DeviceRequestDTO requestDTO) {
        User loggedUser = getAuthenticatedUserEntity();

        Device device = Device.builder()
                .name(requestDTO.name())
                .company(loggedUser.getCompany())
                .build();

        Device savedDevice = deviceRepository.save(device);
        return toDTO(savedDevice);
    }

    public List<DeviceResponseDTO> listDevices() {
        User loggedUser = getAuthenticatedUserEntity();

        return deviceRepository.findAllByCompanyId(loggedUser.getCompany().getId())
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private User getAuthenticatedUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();

        return userRepository.findByEmail(principal.getEmail())
                .orElseThrow(() -> new IllegalStateException("Usuário logado não encontrado no banco"));
    }

    private DeviceResponseDTO toDTO(Device device) {
        return new DeviceResponseDTO(
                device.getId(),
                device.getName(),
                device.getCreatedAt()
        );
    }
}
