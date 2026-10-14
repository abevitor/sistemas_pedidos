package com.example.inventory.event;

import java.math.BigDecimal;

public record PedidoCriadoEvent(Long pedidoId, String clientId, Long produtoId, Integer quantidade, BigDecimal valorTotal) {}
