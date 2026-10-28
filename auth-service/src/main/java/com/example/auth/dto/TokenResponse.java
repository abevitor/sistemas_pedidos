package com.example.auth.dto;

public record TokenResponse(String token,String tipo, long expiraEmSegundos) {
    
}
