package com.example.inventory.event;

public record EstoqueInsuficiente(Long pedidoId, String motivo) {}
