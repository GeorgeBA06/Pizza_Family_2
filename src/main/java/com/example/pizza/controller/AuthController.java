package com.example.pizza.controller;

import com.example.pizza.requestDto.AuthRequestDto;
import com.example.pizza.requestDto.RefreshTokenRequestDto;
import com.example.pizza.responseDto.AuthResponseDto;
import com.example.pizza.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and get JWT tokens")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto requestDto){
        return ResponseEntity.ok(authService.authenticate(requestDto));
    }

    @PostMapping("/register")
    @Operation(summary = "User register", description = "Register new use")
    public ResponseEntity<String> register(@Valid @RequestBody AuthRequestDto requestDto){
       authService.register(requestDto);
       return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh token", description = "Get new access token using refresh token")
    public ResponseEntity<AuthResponseDto> refresh(@Valid @RequestBody RefreshTokenRequestDto refreshDto){
        return ResponseEntity.ok(authService.refreshToken(refreshDto.refreshToken()));
    }

    @PostMapping("/logout")
    @Operation(summary = "User logout", description = "Logout user (client should delete tokens)")
    public ResponseEntity<String> logout(){
        return ResponseEntity.ok(("Logged out successfully"));
    }
}
