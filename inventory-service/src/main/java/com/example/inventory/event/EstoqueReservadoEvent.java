package com.example.inventory.event;

import java.math.BigDecimal;

public record EstoqueReservadoEvent(Long pedidoId, String clienteId, BigDecimal valorTotal) {
}
