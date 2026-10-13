package com.example.demo.dto;

import java.math.BigDecimal;

public record CreateOrderRequest(String clienteId, BigDecimal valorTotal) {}
