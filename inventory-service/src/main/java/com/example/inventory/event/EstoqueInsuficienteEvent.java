package com.example.inventory.event;

public record EstoqueInsuficienteEvent(Long pedidoId, String motivo) {}
