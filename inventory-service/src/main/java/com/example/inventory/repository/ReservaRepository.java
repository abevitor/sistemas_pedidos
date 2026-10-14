package com.example.inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventory.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    boolean existsByPedidoId(Long pedidoId);

    Optional<Reserva> findByPedidoId(Long pedidoId);
    
}
