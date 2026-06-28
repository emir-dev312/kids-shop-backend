package com.kidsshop.backend.service;

import com.kidsshop.backend.dto.UserProfileDto;
import com.kidsshop.backend.entity.User;
import com.kidsshop.backend.exception.ResourceNotFoundException;
import com.kidsshop.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserProfileDto getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        return new UserProfileDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAvatarUrl(), 
                user.getCreatedAt()
        );
    }

    @Transactional
    public void updateAvatar(String email, String avatarUrl) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        user.setAvatarUrl(avatarUrl);
        userRepository.save(user);
    }
}