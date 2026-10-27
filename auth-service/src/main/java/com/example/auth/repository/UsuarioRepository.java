package com.example.auth.repository;

import com.example.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.foreign.Linker.Option;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
    
}
