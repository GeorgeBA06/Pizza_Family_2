package com.example.pizza.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String,Object>> handleBadCredentials(BadCredentialsException e){
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid credentials", e.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, Object>> handleExpiredJwt(ExpiredJwtException e){
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Token expired", e.getMessage());
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Map<String,Object>> handleMalformedJwt(MalformedJwtException e){
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid token", e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String ,Object>> handleAuthentication(AuthenticationException e){
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Authentication failed", e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException e){
        return buildErrorResponse(HttpStatus.NOT_FOUND, "User not found", e.getMessage());}

    private ResponseEntity<Map<String,Object>> buildErrorResponse(HttpStatus status, String message, Object o){
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", LocalDateTime.now());
        map.put("Http Status", status.value());
        map.put("error", o);
        map.put("message", message);

        return new ResponseEntity<>(map, status);
    }
}
