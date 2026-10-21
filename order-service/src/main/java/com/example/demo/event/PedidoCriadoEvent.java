package com.example.demo.event;

import java.math.BigDecimal;

public record PedidoCriadoEvent (Long pedidoId, String clienteId, Long produtoId, Integer quantidade, BigDecimal valorTotal) {}
