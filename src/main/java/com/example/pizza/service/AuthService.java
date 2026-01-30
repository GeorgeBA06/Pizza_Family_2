package com.example.pizza.service;

import com.example.pizza.entity.User;
import com.example.pizza.repository.UserRepository;
import com.example.pizza.requestDto.AuthRequestDto;
import com.example.pizza.responseDto.AuthResponseDto;
import com.example.pizza.security.CustomUserDetails;
import com.example.pizza.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDto authenticate(AuthRequestDto requestDto){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.email(),
                            requestDto.password()
                    )
            );
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();

            String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getRole().name());

            String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

            return new AuthResponseDto(
                    accessToken,
                    refreshToken,
                    user.getEmail(),
                    user.getName(),
                    user.getRole()
            );
        }catch (BadCredentialsException e){
            throw new RuntimeException("Invalid email or password");
        }
    }

    public AuthResponseDto refreshToken(String refreshToken){
        if(!jwtUtil.validateToken(refreshToken)){
            throw new RuntimeException("Invalid refresh token");
        }

        String tokenType = jwtUtil.extractTokenType(refreshToken);

        if(!"refresh".equals(tokenType)){
            throw new RuntimeException("Token is not refresh token");
        }

        String email = jwtUtil.extractEmail(refreshToken);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getRole().name());
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        return new AuthResponseDto(
                newAccessToken,
                newRefreshToken,
                user.getEmail(),
                user.getName(),
                user.getRole()
        );
    }

    public void register(AuthRequestDto authRequestDto){
        if(userRepository.existsByEmail(authRequestDto.email())){
            throw new RuntimeException("User with this email already exists");
        }

        User user = new User();
        user.setEmail(authRequestDto.email());
        user.setPassword(passwordEncoder.encode(authRequestDto.password()));
        user.setName(authRequestDto.email().split("@")[0]);

        userRepository.save(user);
        log.info("User registred: {}", authRequestDto.email());

    }
}
