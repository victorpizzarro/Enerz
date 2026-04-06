package com.enerz.enerz.service;

import com.enerz.enerz.dto.UserResponseDTO;
import com.enerz.enerz.model.User;
import com.enerz.enerz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDTO getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        User freshUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalStateException("Usuário autenticado não encontrado"));

        return UserResponseDTO.from(freshUser);
    }
}
