package com.example.auth.controller;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.RegisterRequest;
import com.example.auth.dto.TokenResponse;
import com.example.auth.dto.UsuarioResponse;
import com.example.auth.model.Usuario;
import com.example.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse registrar(@Valid @RequestBody RegisterRequest request) {
        Usuario usuario = service.registrar(request);
        return new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request) {
        return service.login(request);
    }

    // protegido: só responde com um token válido
    @GetMapping("/me")
    public Map<String, Object> me(@AuthenticationPrincipal Jwt jwt) {
        return Map.of(
                "id", jwt.getSubject(),
                "email", jwt.getClaimAsString("email"),
                "nome", jwt.getClaimAsString("nome"),
                "role", jwt.getClaimAsString("role"));
    }
}