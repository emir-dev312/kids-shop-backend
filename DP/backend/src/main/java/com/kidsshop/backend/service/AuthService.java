package com.kidsshop.backend.service;

import com.kidsshop.backend.dto.LoginRequestDto;
import com.kidsshop.backend.dto.RegisterRequestDto;
import com.kidsshop.backend.dto.TokenResponseDto;
import com.kidsshop.backend.entity.Role;
import com.kidsshop.backend.entity.User;
import com.kidsshop.backend.exception.ResourceNotFoundException;
import com.kidsshop.backend.exception.UserAlreadyExistsException;
import com.kidsshop.backend.repository.UserRepository;
import com.kidsshop.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils; 

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    private static final Set<String> ADMIN_EMAILS = Set.of(
            "emirzaksylykov2@gmail.com",
            "amantay2100@gmail.com",
            "sayakovaa197@gmail.com"
    );

    @Transactional
    public TokenResponseDto register(RegisterRequestDto request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("Email is already in use");
        }

        
        String email = request.email().toLowerCase().trim();


        Role assignedRole = ADMIN_EMAILS.contains(email) 
                ? Role.ADMIN 
                : Role.USER;


        String hash = DigestUtils.md5DigestAsHex(email.getBytes());
        String avatarUrl = "https://www.gravatar.com/avatar/" + hash + "?d=identicon";

        User user = User.builder()
                .name(request.name())
                .email(email)
                .password(passwordEncoder.encode(request.password()))
                .role(assignedRole)
                .avatarUrl(avatarUrl) 
                .build();

        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(savedUser.getEmail(), savedUser.getId(), savedUser.getRole().name());
        
        return new TokenResponseDto(jwtToken);
    }

    @Transactional(readOnly = true)
    public TokenResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String jwtToken = jwtService.generateToken(user.getEmail(), user.getId(), user.getRole().name());
        
        return new TokenResponseDto(jwtToken);
    }
}