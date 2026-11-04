package com.example.inventory.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // mesma chave do auth-service, só que aqui ela só VALIDA, nunca assina
    @Bean
    public JwtDecoder jwtDecoder(@Value("${jwt.secret}") String secret) {
        var chave = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(chave).build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // catálogo de produtos fica público: não é dado sensível,
                // e o order-service também precisa consultar sem se autenticar como usuário
                .anyRequest().permitAll())
            .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));
        return http.build();
    }
}