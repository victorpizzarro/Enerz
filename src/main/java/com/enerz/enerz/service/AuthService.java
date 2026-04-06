package com.enerz.enerz.service;

import com.enerz.enerz.dto.AuthResponseDTO;
import com.enerz.enerz.dto.LoginRequestDTO;
import com.enerz.enerz.dto.RegisterRequestDTO;
import com.enerz.enerz.dto.UserResponseDTO;
import com.enerz.enerz.model.Company;
import com.enerz.enerz.model.Role;
import com.enerz.enerz.model.User;
import com.enerz.enerz.repository.CompanyRepository;
import com.enerz.enerz.repository.UserRepository;
import com.enerz.enerz.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        Company company = Company.builder()
                .name(request.companyName())
                .build();
        company = companyRepository.save(company);

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .company(company)
                .build();
        user = userRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponseDTO.from(token, UserResponseDTO.from(user));
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Credenciais inválidas"));

        String token = jwtService.generateToken(user);

        return AuthResponseDTO.from(token, UserResponseDTO.from(user));
    }
}
