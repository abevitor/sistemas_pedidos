package com.example.auth.service;

import com.example.auth.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    private final JwtEncoder encoder;
    private final long expiracaoSegundos;

    public TokenService(JwtEncoder encoder,
                        @Value("${jwt.expiration-seconds}") long expiracaoSegundos) {
        this.encoder = encoder;
        this.expiracaoSegundos = expiracaoSegundos;
    }

    public String gerar(Usuario usuario) {
        Instant agora = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("auth-service")
                .subject(String.valueOf(usuario.getId()))
                .issuedAt(agora)
                .expiresAt(agora.plusSeconds(expiracaoSegundos))
                .claim("email", usuario.getEmail())
                .claim("nome", usuario.getNome())
                .claim("role", usuario.getRole().name())
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    public long getExpiracaoSegundos() {
        return expiracaoSegundos;
    }
}
