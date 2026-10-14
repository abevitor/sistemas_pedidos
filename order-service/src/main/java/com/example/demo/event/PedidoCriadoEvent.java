package com.example.demo.event;

import java.math.BigDecimal;

public record PedidoCriadoEvent (Long pedidoId, String clientId, Long produtoId, Integer quantidade, BigDecimal valorTotal) {}
