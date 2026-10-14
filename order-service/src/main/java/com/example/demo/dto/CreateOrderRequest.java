package com.example.demo.dto;

import java.math.BigDecimal;

public record CreateOrderRequest(Long pedidoId,String clienteId, Long produtoId, Integer quantidade, BigDecimal valorTotal) {}
