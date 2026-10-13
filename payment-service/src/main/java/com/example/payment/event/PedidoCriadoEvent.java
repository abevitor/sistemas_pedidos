package com.example.payment.event;

import java.math.BigDecimal;

public record PedidoCriadoEvent(Long pedidoId, String clienteId, BigDecimal valorTotal) {}
